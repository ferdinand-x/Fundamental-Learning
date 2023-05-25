package com.paradise.code.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public  class RedisLock {

        private StringRedisTemplate stringRedisTemplate;

        public RedisLock(StringRedisTemplate stringRedisTemplate) {
            this.stringRedisTemplate = stringRedisTemplate;
        }

        public RLock newLock(String redisKey) {
            return new RLock(stringRedisTemplate, redisKey, UUID.randomUUID().toString());
        }

        private static class LockStatus {

            private boolean lock;
            private long ttl;

            public boolean isLock() {
                return lock;
            }

            public long getTtl() {
                return ttl;
            }

            public LockStatus(boolean lock, long ttl) {
                this.lock = lock;
                this.ttl = ttl;
            }
        }

        public static class RLock {

            private static String UNLOCK = "unlock";

            private StringRedisTemplate stringRedisTemplate;
            private String redisKey;
            private String lockId;

            public RLock(StringRedisTemplate stringRedisTemplate, String redisKey, String lockId) {
                this.stringRedisTemplate = stringRedisTemplate;
                this.redisKey = redisKey;
                this.lockId = lockId;
            }

            public void lock(long lockTimeout, TimeUnit unit) {
                stringRedisTemplate.execute(new RedisCallback<Void>() {
                    @Override
                    public Void doInRedis(RedisConnection connection) throws DataAccessException {
                        CompletableFuture future = new CompletableFuture();
                        RedisSerializer<String> keySerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
                        byte[] key = keySerializer.serialize(redisKey);
                        //订阅锁释放消息
                        connection.subscribe((message, pattern) -> {
                            String msg = new String(message.getBody());
                            if (UNLOCK.equals(msg)) {
                                future.complete(message);
                            }
                        }, key);
                        //tryLock
                        LockStatus lockStatus;
                        do {
                            lockStatus = _tryLock(lockTimeout, unit);
                            if (!lockStatus.isLock()) {
                                try {
                                    future.get(lockStatus.getTtl(), TimeUnit.MILLISECONDS);
                                } catch (ExecutionException e) {
                                    throw new RuntimeException(e.getMessage(), e);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                } catch (TimeoutException ignored) {
                                }
                            }
                        } while (!lockStatus.isLock());
                        return null;
                    }
                });
            }

            public boolean tryLock(long lockTimeout, TimeUnit unit) {
                LockStatus lockStatus = _tryLock(lockTimeout, unit);
                return lockStatus.isLock();
            }

            public LockStatus _tryLock(long lockTimeout, TimeUnit unit) {
                LockStatus lock = stringRedisTemplate.execute(new RedisCallback<LockStatus>() {
                    @Override
                    public LockStatus doInRedis(RedisConnection connection) throws DataAccessException {
                        RedisSerializer<String> keySerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
                        RedisSerializer<String> valueSerializer = (RedisSerializer<String>) stringRedisTemplate.getValueSerializer();
                        byte[] key = keySerializer.serialize(redisKey);
                        long ttl = TimeoutUtils.toMillis(lockTimeout, unit);
                        long expireTime = System.currentTimeMillis() + ttl;
                        byte[] value = valueSerializer.serialize(String.format("%s:%s", lockId, expireTime));
                        String data;
                        do {
                            byte[] bytes = connection.get(key);
                            data = valueSerializer.deserialize(bytes);
                            if (null == data) {
                                //setNX将key的值设为value，当且仅当key不存在。
                                //若给定的key已经存在，则SETNX不做任何动作。
                                Boolean ret = connection.setNX(key, value);
                                if (!ret) {
                                    continue;
                                }
                                //获取锁成功
                                return new LockStatus(true, ttl);
                            }
                        } while (null == data);
                        return new LockStatus(false, getTTL(data));
                    }
                });
                return lock;
            }

            private long getTTL(String data) {
                String[] split = data.split(":");
                Long expireTime = Long.parseLong(split[1]);
                long ttl = expireTime - System.currentTimeMillis();
                return ttl;
            }

            private String getLockId(String data) {
                String[] split = data.split(":");
                String lockId = split[0];
                return lockId;
            }

            public void unlock() {
                stringRedisTemplate.execute(new RedisCallback<Void>() {
                    @Override
                    public Void doInRedis(RedisConnection connection) throws DataAccessException {
                        RedisSerializer<String> keySerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
                        RedisSerializer<String> valueSerializer = (RedisSerializer<String>) stringRedisTemplate.getValueSerializer();
                        byte[] key = keySerializer.serialize(redisKey);
                        byte[] value = connection.get(key);
                        String data = valueSerializer.deserialize(value);
                        if (null == data) {
                            //ttl超时。做业务回滚处理
                            throw new RuntimeException("锁超时");
                        }
                        if (getTTL(data) <= 0) {
                            //ttl超时。做业务回滚处理
                            throw new RuntimeException("锁超时");
                        }
                        if (!lockId.equals(getLockId(data))) {
                            //lockId不一样，锁被替换。做业务回滚处理
                            throw new RuntimeException("锁超时");
                        }

                        //如果在事务执行之前这key被其他命令所改动，那么下面事务将被打断
                        connection.watch(key);
                        try {
                            //标记一个事务块的开始
                            connection.multi();
                            //删除key
                            connection.del(key);
                            //执行所有事务块内的命令
                            List<Object> ret = connection.exec();
                            if (null == ret) {
                                //watch后被其他命令所改动
                                throw new RuntimeException("锁超时");
                            }
                            byte[] msg = valueSerializer.serialize(UNLOCK);
                            //发布锁释放消息
                            connection.publish(key, msg);
                        } finally {
                            //取消WATCH命令对key的监视
                            connection.unwatch();
                        }
                        return null;
                    }
                });
            }
        }
    }

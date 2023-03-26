package com.paradise.code.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisLock {
    // 创建一个RedisTemplate对象
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 定义一个ThreadLocal变量，用于存储每个线程持有的锁标识
    private static final ThreadLocal<String> LOCK_VALUE = new ThreadLocal<>();

    // 定义一个过期时间（秒）
    private static final long EXPIRE_TIME = 10;

    // 获取锁
    public boolean getLock(final String lockKey) {
        // 生成一个UUID作为值
        String value = UUID.randomUUID().toString();
        boolean result;
        try {
            // 尝试执行set命令，并指定nx和ex参数
            redisTemplate.opsForValue().set(lockKey, value, EXPIRE_TIME, TimeUnit.SECONDS);
            // 如果返回成功，则表示获取到了锁，并将值存入ThreadLocal变量中
            LOCK_VALUE.set(value);
            result = true;
        } catch (Exception e) {
            // ignore
            result = false;
        }
        // 否则表示未获取到锁，返回false
        return result;
    }

    // 释放锁
    public void releaseLock(final String lockKey) {
        // 获取当前线程持有的值
        String value = LOCK_VALUE.get();
        // 如果当前值不为空，则表示当前线程持有了该键
        if (value != null) {
            // 定义一个Lua脚本字符串（注意转义字符）
            String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 0 end";
            // 使用eval命令执行Lua脚本，并传入键和值作为参数（注意类型转换）
            Long result = (Long) redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(lockKey), value);
            // 如果返回结果为1，则表示删除成功，并清除ThreadLocal变量中的值；否则表示删除失败或者已经被其他客户端删除了。
            if (result != null && result == 1L) {
                LOCK_VALUE.remove();
            }
        }
    }
}

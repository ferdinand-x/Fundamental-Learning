package com.paradise.code.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ferdinand
 */
@Component
@RequiredArgsConstructor
public class DistributedLock {
    private static final long DEFAULT_EXPIRE_TIME = 10;
    private static final String LOCK_PREFIX = "GVN:LOCK:";

    private static final long MAX_WAIT_TIME = 50000;
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    private final ThreadLocal<String> lockHolder = new ThreadLocal<>();

    private final StringRedisTemplate redisTemplate;

    public boolean acquireLock(String lockKey) {
        return acquireLock(lockKey, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public boolean acquireLock(String key, long expireTime, TimeUnit timeUnit) {
        long expireSec = timeUnit.toSeconds(expireTime);
        String lockKey = LOCK_PREFIX + key;
        String lockVal = UUID.randomUUID().toString();

        long startTime = System.currentTimeMillis();
        long maxWaitTime = startTime + MAX_WAIT_TIME;

        while (System.currentTimeMillis() < maxWaitTime) {
            try {
                Boolean success = redisTemplate.opsForValue().setIfAbsent(key, lockVal, expireSec, TimeUnit.SECONDS);
                if (success != null && success) {
                    lockHolder.set(lockVal);
                    this.renewExpire(lockKey, lockVal, expireSec);
                    return true;
                }
            } catch (Exception ignore) {

            }
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    private void renewExpire(String lockKey, String lockVal, long expireSec) {
        long expireMs = TimeUnit.SECONDS.toMillis(expireSec);
        long renewIntervalMs = expireMs / 3;
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            String cacheVal = redisTemplate.opsForValue().get(lockKey);
            if (Objects.equals(cacheVal, lockVal)) {
                redisTemplate.opsForValue().set(lockKey, lockVal, expireSec, TimeUnit.SECONDS);
            }
        }, renewIntervalMs, renewIntervalMs, TimeUnit.MILLISECONDS);
    }

    public void releaseLock(String lockKey) {
        String key = LOCK_PREFIX + lockKey;
        String lockValue = lockHolder.get();
        String currentValue = redisTemplate.opsForValue().get(key);

        if (Objects.equals(lockValue, currentValue)) {
            redisTemplate.delete(key);
            lockHolder.remove();
        }
    }
}

package com.paradise.code.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ferdinand
 */
@Component
@RequiredArgsConstructor
public class DistributedLock {
    private static final long DEFAULT_EXPIRE_TIME = 30;
    private static final long MAX_RETRY_TIMES = 3;
    private static final String LOCK_PREFIX = "GVN:LOCK:";

    private final ThreadLocal<String> lockHolder = new ThreadLocal<>();

    private final StringRedisTemplate redisTemplate;

    public boolean acquireLock(String lockKey) throws InterruptedException {
        return acquireLock(lockKey, DEFAULT_EXPIRE_TIME,TimeUnit.SECONDS);
    }

    public boolean acquireLock(String lockKey, long expireTime,TimeUnit timeUnit) throws InterruptedException {
        long expire = timeUnit.toSeconds(expireTime);
        String key = LOCK_PREFIX + lockKey;
        String value = UUID.randomUUID().toString();
        long retryTimes = 0;

        while (retryTimes < MAX_RETRY_TIMES) {
            Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);
            if (success != null && success) {
                lockHolder.set(value);
                return true;
            }
            retryTimes++;
            TimeUnit.MILLISECONDS.sleep(50);
        }
        return false;
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

package com.cabin.commons.redis.Lock;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisDistributedLock implements DistributedLock {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取锁
     *
     * @param lockKey    锁的 key
     * @param requestId  请求 ID，用于区分不同的请求
     * @param expireTime 锁的过期时间，单位为秒
     * @return 是否获取到锁
     */
    @Override
    public boolean tryLock(String lockKey, String requestId, int expireTime) {
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId);
        if (locked != null && locked) {
            redisTemplate.expire(lockKey, expireTime, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param lockKey   锁的 key
     * @param requestId 请求 ID，用于区分不同的请求
     * @return 是否释放锁成功
     */
    @Override
    public boolean releaseLock(String lockKey, String requestId) {
        Long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            byte[] keyBytes = lockKey.getBytes();
            byte[] valBytes = requestId.getBytes();
            return connection.eval(
                    ("if redis.call('get', KEYS[1]) == ARGV[1] then " +
                            "return redis.call('del', KEYS[1]) else return 0 end").getBytes(),
                    ReturnType.INTEGER, 1, keyBytes, valBytes);
        });
        return result != null && result > 0;
    }
}

package com.cabin.commons.redis.Lock;

public interface DistributedLock {

    /**
     * 获取锁
     *
     * @param lockKey    锁的 key
     * @param requestId  请求 ID，用于区分不同的请求
     * @param expireTime 锁的过期时间，单位为秒
     * @return 是否获取到锁
     */
    boolean tryLock(String lockKey, String requestId, int expireTime);

    /**
     * 释放锁
     *
     * @param lockKey   锁的 key
     * @param requestId 请求 ID，用于区分不同的请求
     * @return 是否释放锁成功
     */
    boolean releaseLock(String lockKey, String requestId);
}

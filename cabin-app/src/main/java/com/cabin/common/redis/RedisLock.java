package com.cabin.common.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;


/**
 * @author 伍六七
 * @date 2023/5/23 9:13
 */
public class RedisLock {
    //锁名称
    public static final String LOCK_PREFIX = "redis_lock:";
    //加锁失效时间，毫秒
    public static final int DEFAULT_LOCK_EXPIRE = 300; // ms

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @param lockKey    锁名(会自动加前缀redis_lock:)
     * @param requestId  唯一标识
     * @param expireTime 秒
     * @return 是否成功
     */
    public boolean tryLock(String lockKey, String requestId, int expireTime) {
        lockKey = LOCK_PREFIX + lockKey;
        //在 setIfAbsent() 方法调用中，如果返回值为 true，则表示获取锁成功，否则表示已经有其它线程持有锁，确保了多个线程之间的互斥性。
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId);
        if (locked != null && locked) {
            redisTemplate.expire(lockKey, expireTime, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }


}

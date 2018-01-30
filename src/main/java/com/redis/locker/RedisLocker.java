package com.redis.locker;

/**
 * Created by zhangguilin on 1/30/2018.
 */
public interface RedisLocker {
    Boolean acquireLockWithTimeout(int lockKeyExpireSecond,String lockKey,Boolean isWait) throws Exception;
    Boolean releaseLockWithTimeout(String lockKey) throws Exception;
}

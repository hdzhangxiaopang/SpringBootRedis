package com.redis.locker.impl;

import com.redis.locker.RedisLocker;
import com.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by zhangguilin on 1/30/2018.
 */
@Service
public class RedisLockerImpl implements RedisLocker {

    private Logger logger = LoggerFactory.getLogger(RedisLocker.class);

    /**
     * 当前机器节点锁标识
     * */
    private static String redisIdentityKey = UUID.randomUUID().toString();

    /**
     * 获取当前机器节点在锁中的标示符
     * */

    public static String getRedisIdentityKey(){
        return redisIdentityKey;
    }

    /**
     * 等待获取锁的时间，可以根据当前任务的执行时间来设置。
     * 设置的太短，浪费CPU，设置的太长锁就不公平。
     * */
    private static final long WaitLockTimeSecond = 2000;

    /**
     * 重试获取锁的次数，可以根据当前任务的执行时间来设置。
     * 需要时间==RetryCount*(WaitLockTimeSecond/1000)
     * */
    private static final int RetryCount = 10;

    @Autowired
    private RedisService redisService;

    /**
     * 带超时时间的redis lock
     *
     * lockKeyExpireSecond  锁key在redis中的过去时间
     * lockKey
     * isWait 当获取不到锁时是否需要等待
     * Exception lockKey is empty throw exception
     * */

    @Override
    public Boolean acquireLockWithTimeout(int lockKeyExpireSecond, String lockKey, Boolean isWait) throws Exception {
        if(StringUtils.isEmpty(lockKey)) throw new Exception("lockKey is Empty");
        int retryCounts = 0;
        while(true){
            boolean status = false;
            status = redisService.cacheValueIfExist(lockKey,redisIdentityKey,lockKeyExpireSecond);
            if(status){
                logger.info(String.format("t:%s,当前节点：%s,获取到锁：%s", Thread.currentThread().getId(), getRedisIdentityKey(), lockKey));
                return true;
            }

            try {
                if(isWait && retryCounts < RetryCount){
                    retryCounts++;
                    synchronized (this){
                        logger.info(String.
                                format("t:%s,当前节点：%s,尝试等待获取锁：%s", Thread.currentThread().getId(), getRedisIdentityKey(), lockKey));
                        this.wait(WaitLockTimeSecond);
                    }
                }else if(retryCounts == RetryCount){
                    logger.info(String.
                            format("t:%s,当前节点：%s,指定时间内获取锁失败：%s", Thread.currentThread().getId(), getRedisIdentityKey(), lockKey));
                    return false;
                }else{
                    return false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Boolean releaseLockWithTimeout(String lockKey) throws Exception {
        if(StringUtils.isEmpty(lockKey)) throw new Exception("lockKey is Empty");

        boolean status = redisService.removeValue(lockKey);
        if(status){
            logger.info(String.
                    format("t:%s,当前节点：%s,释放锁：%s 成功。", Thread.currentThread().getId(), getRedisIdentityKey(), lockKey));
            return true;
        }
        logger.info(String.
                format("t:%s,当前节点：%s,释放锁：%s 失败。", Thread.currentThread().getId(), getRedisIdentityKey(), lockKey));
        return false;
    }
}

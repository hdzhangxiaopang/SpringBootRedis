package com.redis.controller;

import com.redis.locker.RedisLocker;
import com.redis.locker.impl.RedisLockerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangguilin on 1/30/2018.
 */
@RestController
@RequestMapping("/redis")
public class LockerController {

    @Autowired
    private RedisLocker redisLocker;

    @RequestMapping(value = "/lock",produces = "application/json;charset=utf-8",method = RequestMethod.GET)
    public boolean acquireLock(){
        try {
            return redisLocker.acquireLockWithTimeout(50,"product:10000000:shopping",true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping(value = "/releaseLock",produces = "application/json;charset=utf-8",method = RequestMethod.GET)
    public boolean releaseLock(){
        try {
            return redisLocker.releaseLockWithTimeout("product:10000000:shopping");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping(value = "/getLockIdentity", produces = "application/json;charset=utf-8",method = RequestMethod.GET)
    public String getCurrentNodeLockIdentity(){
        return RedisLockerImpl.getRedisIdentityKey();
    }

}

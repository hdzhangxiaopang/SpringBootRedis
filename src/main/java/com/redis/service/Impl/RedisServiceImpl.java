package com.redis.service.Impl;

import com.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangguilin on 1/25/2018.
 */
@Service
public class RedisServiceImpl implements RedisService {
    /**
     * 前缀
     * */
    private static final String KEY_PREFIX_VALUE = "key:prefix:value:";
    private static final String KEY_PREFIX_SET = "key:prefix:set:";
    private static final String KEY_PREFIX_LIST = "key:prefix:list:";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 日志记录
     * */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean cacheValue(String k, String value, long time) {
        String key = KEY_PREFIX_VALUE + k;
        try {
            ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            if(time>0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            LOGGER.error("缓存["+key+"]失败,value["+value+"]",t);
            t.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cacheValue(String key, String value) {

        return cacheValue(key,value,-1);
    }

    @Override
    public boolean cacheValueIfExist(String k, String value) {

        return false;
    }

    @Override
    public boolean chacheValueIfExist(String k, String value, long time) {
        String key = KEY_PREFIX_VALUE + k;
        Boolean result = false;
        try {
            ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
            valueOperations.setIfAbsent(k,value);
            if(time>0){
                redisTemplate.expire(key,time,TimeUnit.SECONDS);
            }
        } catch (Throwable t) {
            LOGGER.error("缓存[" + key + "]失败, value[" + value + "]", t);
        }
        return result;
    }

    @Override
    public boolean containsValueKey(String key) {
        return false;
    }

    @Override
    public boolean containsSetKey(String key) {
        return false;
    }

    @Override
    public boolean containsListKey(String key) {
        return false;
    }

    @Override
    public boolean containsKey(String key) {
        return false;
    }

    @Override
    public String getValue(String key) {
        try {
            ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
            return valueOperations.get(KEY_PREFIX_VALUE+key);
        } catch (Throwable t) {
            LOGGER.error("获取缓存失败["+KEY_PREFIX_VALUE+key+"],"+t);
        }
        return null;
    }

    @Override
    public boolean removeValue(String key) {
       return remove(key);
    }

    @Override
    public boolean removeSet(String key) {
        return false;
    }

    @Override
    public boolean removeList(String key) {
        return false;
    }

    @Override
    public boolean cacheSet(String key, String value, long time) {
        return false;
    }

    @Override
    public boolean cacheSet(String k, Set<String> v, long time) {
        return false;
    }

    @Override
    public boolean cacheSet(String k, Set<String> v) {
        return false;
    }

    @Override
    public Set<String> getSet(String k) {
        return null;
    }

    @Override
    public boolean cacheList(String k, String v, long time) {
        return false;
    }

    @Override
    public boolean cacheList(String k, String v) {
        return false;
    }

    @Override
    public boolean cacheList(String k, List<String> v, long time) {
        return false;
    }

    @Override
    public boolean cacheList(String k, List<String> v) {
        return false;
    }

    @Override
    public List<String> getList(String k, long start, long end) {
        return null;
    }

    @Override
    public long getListSize(String key) {
        return 0;
    }

    @Override
    public long getListSize(ListOperations<String, String> listOps, String k) {
        return 0;
    }

    @Override
    public boolean removeOneOfList(String k) {
        return false;
    }

    public boolean remove(String key){
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            LOGGER.error("删除指定内容失败key["+key+"],error:"+e);
        }
        return false;
    }
}

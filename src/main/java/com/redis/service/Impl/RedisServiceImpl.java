package com.redis.service.Impl;

import com.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
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

        return cacheValueIfExist(k,value,-1);
    }

    @Override
    public boolean cacheValueIfExist(String k, String value, long time) {
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
        return containsValueKey(KEY_PREFIX_VALUE+key);
    }

    @Override
    public boolean containsSetKey(String key) {
        return containsKey(KEY_PREFIX_SET+key);
    }

    @Override
    public boolean containsListKey(String key) {
        return containsKey(KEY_PREFIX_LIST+key);
    }

    @Override
    public boolean containsKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable t) {
            LOGGER.error("判断缓存存在失败key["+key+",error: "+t);
        }
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

    /**
     * 移除缓存
     * */
    @Override
    public boolean removeOneOfList(String k) {
        String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            listOperations.rightPop(key);
            return true;
        } catch (Throwable t) {
            LOGGER.error("移除list缓存失败key["+k+"],eroor: "+t);
        }
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

    @Override
    public boolean removeValue(String key) {
       return remove(KEY_PREFIX_VALUE+key);
    }

    @Override
    public boolean removeSet(String key) {
        return remove(KEY_PREFIX_SET+key);
    }

    @Override
    public boolean removeList(String key) {
        return remove(KEY_PREFIX_LIST+key);
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
        try {
            SetOperations setOperations = redisTemplate.opsForSet();
            setOperations.members(KEY_PREFIX_SET + k);
        } catch (Throwable t) {
            LOGGER.error("获取set缓存失败key["+KEY_PREFIX_SET + k+"],error: "+t);
        }
        return null;
    }

    @Override
    public boolean cacheList(String k, String v, long time) {
        String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            listOperations.rightPush(k,v);
            if(time>0){
                redisTemplate.expire(k,time,TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            LOGGER.error("缓存["+key+"],error: "+t);
        }
        return false;
    }

    @Override
    public boolean cacheList(String k, String v) {
        return cacheList(k,v,-1);
    }

    @Override
    public boolean cacheList(String k, List<String> v, long time) {
        String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            listOperations.rightPushAll(key,v,time);
            if(time>0){
                redisTemplate.expire(key,time,TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            LOGGER.error("缓存["+key+"]失败,value["+v+"]",t);
        }
        return false;
    }

    @Override
    public boolean cacheList(String k, List<String> v) {
        return cacheList(k,v,-1);
    }

    @Override
    public List<String> getList(String k, long start, long end) {
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            return listOperations.range(KEY_PREFIX_LIST+k,start,end);
        } catch (Throwable t) {
            LOGGER.error("获取list缓存失败key["+KEY_PREFIX_LIST+k+"],error: "+t);
        }
        return null;
    }

    @Override
    public long getListSize(String key) {
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            return listOperations.size(KEY_PREFIX_LIST + key);
        } catch (Throwable t) {
            LOGGER.error("获取list长度失败key["+KEY_PREFIX_LIST + key+"],error: "+t);
        }
        return 0;
    }

    @Override
    public long getListSize(ListOperations<String, String> listOps, String k) {
        try {
            return listOps.size(KEY_PREFIX_LIST+k);
        } catch (Throwable t) {
           LOGGER.error("获取list长度失败key["+KEY_PREFIX_LIST+k+"],eroor: "+t);
        }
        return 0;
    }

}
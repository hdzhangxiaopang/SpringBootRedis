package com.redis.service;

import org.springframework.data.redis.core.ListOperations;

import java.util.List;
import java.util.Set;

/**
 * Created by zhangguilin on 1/25/2018.
 */
public interface RedisService {

    /**
     * 添加
     * */
    boolean cacheValue(String key,String value,long time);

    /**
     * 添加
     * */
    boolean cacheValue(String key,String value);

    /**
     * key存在的时候添加,否则不添加
     * */
    boolean cacheValueIfExist(String key,String value);

    /**
     * key存在的时候添加,否则不添加
     * */
    boolean chacheValueIfExist(String key,String value,long time);

    /**
     * 是否包含
     * */
    boolean containsValueKey(String key);

    /**
     * 是否包含
     * */
     boolean containsSetKey(String key);

    /**
     * 是否包含
     * */
    boolean containsListKey(String key);

    /**
     * 是否包含
     * */
    boolean containsKey(String key);

    /**
     * 获取缓存
     * */
    String getValue(String key);

    /**
     * 移除缓存
     * */
    boolean removeValue(String key);

    /**
     * 移除缓存Set
     * */
    boolean removeSet(String key);

    /**
     * 移除缓存List
     * */
    boolean removeList(String key);

    /**
     * 缓存Set
     * */
    boolean cacheSet(String key,String value,long time);

    /**
     * 缓存Set
     * */
    boolean cacheSet(String k, Set<String> v,long time);

    /**
     * 缓存Set
     * */
    boolean cacheSet(String k,Set<String> v);

    /**
     * 获取Set
     * */
    Set<String> getSet(String k);

    /**
     * 缓存List
     * */
    boolean cacheList(String k,String v,long time);

    /**
     * 缓存List
     * */
    boolean cacheList(String k,String v);

    /**
     * 缓存List
     * */
    boolean cacheList(String k, List<String> v,long time);

    /**
     * 缓存List
     * */
    boolean cacheList(String k, List<String> v);

    /**
     * 获取List
     * */
    List<String> getList(String k,long start,long end);

    /**
     * 获取页码
     * */
    long getListSize(String key);

    /**
     * 获取页码
     * */
    long getListSize(ListOperations<String,String> listOps,String k);

    /**
     * 移除list缓存
     * */
    boolean removeOneOfList(String k);



}

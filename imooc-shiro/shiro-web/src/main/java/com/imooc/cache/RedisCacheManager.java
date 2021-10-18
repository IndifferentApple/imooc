package com.imooc.cache;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/10/18 20:16
 */
public class RedisCacheManager implements CacheManager {


    @Resource
    private RedisCache redisCache;
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}

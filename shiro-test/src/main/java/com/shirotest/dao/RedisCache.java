package com.shirotest.dao;

import com.shirotest.util.Jedisutil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

@Component
public class RedisCache<K, V> implements Cache<K, V> {

    /*
    * 自定义对缓存的操作，注给spring容器，实现Cache的自主管理
    * */

    @Autowired
    private Jedisutil jedisutil;

    private final String CACHE_PREFIX = "shiro_test_cache";

    private byte[] getKey(K k) {
        if (k instanceof String) {
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Override
    public V get(K k) throws CacheException {
        byte[] value = jedisutil.get(getKey(k));
        if (null != value) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisutil.set(key, value);
        jedisutil.expire(key, 600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = jedisutil.get(key);
        jedisutil.del(key);
        if (null != value) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        /*
        * 若Redis中还有其他的系统数据，重写该方法需要慎重，应为该方法会清除掉Redis中所有的数据
        * 但是若Redis中只有shiro相关的数据便可以按需求重写
        * */
    }

    @Override
    public int size() {
        if(null != keys()){
            return  keys().size();
        }
        return 0;
    }

    @Override
    public Set<K> keys() {
        //读取所有的缓存中的键
        return null;
    }

    @Override
    public Collection<V> values() {
        //读取所有缓存中的值
        return null;
    }
}

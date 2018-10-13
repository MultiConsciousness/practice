package com.shirotest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class Jedisutil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getResource() {
        return jedisPool.getResource();
    }


    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(key,value);
        }finally {
            jedis.close();
        }
        return value;
    }

    public void expire(byte[] key, int i) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.expire(key, i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }

    }

    public byte[] get(byte[] key) {
        System.out.println("-------------------------从redis读取数据----------------------");
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return value;
    }

    public void del(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    public Set<byte[]> keys(String shiroSessionPrefix) {
        Jedis jedis = null;
        Set<byte[]> activeUser = null;
        try {
            jedis = getResource();
            activeUser = jedis.keys((shiroSessionPrefix + "*").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return activeUser;
    }
}

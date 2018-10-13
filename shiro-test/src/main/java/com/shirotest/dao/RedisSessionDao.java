package com.shirotest.dao;

import com.shirotest.util.Jedisutil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RedisSessionDao extends AbstractSessionDAO {

    /*
    * 自定义对缓存中Session的操作
    * 由于是由我们选择的redis作为缓存，需要重写对于不同缓存的操作方式(CRUD)
    * */

    @Autowired
    private Jedisutil jedisutil;

    private final static String SHIRO_SESSION_PREFIX = "shirotest-session";

    private byte[] getKey(String key) {
        return (SHIRO_SESSION_PREFIX + key).getBytes();
    }

    private void saveSession(Session session) {
        if (null != session && null != session.getId()) {
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisutil.set(key, value);
            jedisutil.expire(key, 600);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        if (null == serializable) {
            return null;
        }
        byte[] key = getKey(serializable.toString());
        byte[] value = jedisutil.get(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (null == session || null == session.getId()) {
            return;
        }
        byte[] key = getKey(session.getId().toString());
        jedisutil.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisutil.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<>();
        if(CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for(byte[] key : keys){
            Session session= (Session) SerializationUtils.deserialize(key);
            sessions.add(session);
        }
        return sessions;
    }
}

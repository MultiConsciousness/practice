package com.shirotest.dao;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.stereotype.Repository;

import javax.servlet.ServletRequest;
import java.io.Serializable;

@Repository
public class CustomSessionManager extends DefaultWebSessionManager {

    /*
    * 避免反复读取Redis,第一次读取之后将session数据写入Request中
    * */

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }
        if(null != request && null != sessionId){
            Session session = (Session)request.getAttribute(sessionId.toString());
            if(null != session){
                return session;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if(null != request && null != session && null != sessionId){
            request.setAttribute(sessionId.toString(),session);
        }
        return session;
    }
}

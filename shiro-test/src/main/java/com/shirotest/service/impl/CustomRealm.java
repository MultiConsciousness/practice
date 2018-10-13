package com.shirotest.service.impl;

import com.shirotest.po.Permission;
import com.shirotest.po.Role;
import com.shirotest.po.UserInfo;
import com.shirotest.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService infoService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("---------授权器--------");
        String username = (String) principalCollection.getPrimaryPrincipal();
        Role role = infoService.findRoleByName(username);
        List<Permission> permissions = infoService.findPermissionByName(username);
        Set<String> roles = new HashSet<>();
        roles.add(role.getRoleName());
        Set<String> pers = new HashSet<>();
        for(Permission p : permissions){
            pers.add(p.getPermissionName());
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(pers);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------认证器-----------");
        String username = (String) authenticationToken.getPrincipal();
        UserInfo info = infoService.findInfoByName(username);
        if(null == info){
            return null;
        }
        String password = info.getPassword();
        String salt = info.getSalt();
        info.setPassword(null);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,password, ByteSource.Util.bytes(salt),"customeRealm");
        return authenticationInfo;
    }
}

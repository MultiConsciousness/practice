package com.shirotest.service;

import com.shirotest.po.Permission;
import com.shirotest.po.Role;
import com.shirotest.po.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo findInfoByName(String username);
    Role findRoleByName(String username);
    List<Permission> findPermissionByName(String username);
}

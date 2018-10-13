package com.shirotest.service.impl;

import com.shirotest.mapper.*;
import com.shirotest.po.Permission;
import com.shirotest.po.Role;
import com.shirotest.po.UserInfo;
import com.shirotest.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper infoMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public UserInfo findInfoByName(String username) {
        System.out.println("-------------------------从数据库读取数据----------------------");
        if (null == username) {
            return null;
        }
        return infoMapper.findInfoByName(username);
    }

    @Override
    public Role findRoleByName(String username) {
        if (null != username) {
            UserInfo info = infoMapper.findInfoByName(username);
            if (null != info) {
                Integer roleId = userRoleMapper.selectByUserId(info.getInfoId());
                if (null != roleId) {
                    Role role = roleMapper.selectByRole(roleId);
                    if (null != role) {
                        return role;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public List<Permission> findPermissionByName(String username) {
        List<Permission> permissions = new ArrayList<>();
        if (null != username) {
            Role role = findRoleByName(username);
            if (null != role && 0 != role.getRoleId()) {
                List<Integer> permissionIds = rolePermissionMapper.selectByRoleId(role.getRoleId());
                if (null != permissionIds && permissionIds.size() > 0) {
                    for(Integer id : permissionIds){
                        List<Permission> pers = permissionMapper.selectById(id);
                        permissions.addAll(pers);
                    }
                    if (null != permissions && permissions.size() > 0) {
                        return permissions;
                    }
                }
            }
        }
        return null;
    }
}

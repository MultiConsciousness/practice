package com.shirotest.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
    List<Integer> selectByRoleId(@Param("roleId") int roleId);
}

package com.shirotest.mapper;

import com.shirotest.po.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {

        List<Permission> selectById(@Param("permissionId") int permissionId);

}

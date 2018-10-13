package com.shirotest.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    int selectByUserId(@Param("userId") int userId);
    int selectByRoleId(@Param("roleId")int roleId);
}

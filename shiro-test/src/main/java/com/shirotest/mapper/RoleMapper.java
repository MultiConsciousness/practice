package com.shirotest.mapper;

import com.shirotest.po.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {

    Role selectByRole(@Param("roleId") int roleId);

}

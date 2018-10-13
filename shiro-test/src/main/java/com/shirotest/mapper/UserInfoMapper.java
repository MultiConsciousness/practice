package com.shirotest.mapper;

import com.shirotest.po.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {

    UserInfo findInfoByName(@Param("username") String username);

}

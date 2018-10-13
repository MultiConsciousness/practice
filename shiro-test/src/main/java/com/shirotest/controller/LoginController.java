package com.shirotest.controller;

import com.shirotest.po.Role;
import com.shirotest.po.UserInfo;
import com.shirotest.service.UserInfoService;
import com.shirotest.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    private UserInfoService infoService;

    @RequestMapping("/login")
    @ResponseBody
    public Result login(UserInfo info){
        UsernamePasswordToken token = new UsernamePasswordToken(info.getUsername(),info.getPassword());
        Subject subject = SecurityUtils.getSubject();
        if(null == subject){
            System.out.println("-------subject is null ------");
        }
        try {
            token.setRememberMe(info.isRememberMe());
            subject.login(token);
            Role role = infoService.findRoleByName(info.getUsername());
            if("admin".equalsIgnoreCase(role.getRoleName())){
                return Result.normal("登陆成功","----roleName:----"+role.getRoleName());
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return Result.unnormal("登录失败");
    }

    @RequiresPermissions("user:query")
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        return "testRole";
    }


    @RequiresRoles("admin,admin1")
    @RequestMapping(value = "/testRole1",method = RequestMethod.GET)
    @ResponseBody
    public String testRole1(){
        return "testRole1";
    }

}

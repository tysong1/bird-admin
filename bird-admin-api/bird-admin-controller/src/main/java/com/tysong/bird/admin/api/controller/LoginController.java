package com.tysong.bird.admin.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.tysong.bird.admin.api.common.ApiResponse;
import com.tysong.bird.admin.common.bean.UserBean;
import com.tysong.bird.admin.dao.entity.UserEntity;
import com.tysong.bird.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
public class LoginController extends BaseController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ApiResponse login(@RequestBody String body) {
        JSONObject rootObject=JSONObject.parseObject(body);
        String username = rootObject.getString("username");
        String password = rootObject.getString("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ApiResponse.busionessError("请求参数错误");
        }
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(new UsernamePasswordToken(username, password));
        }catch (Exception ex){
            log.error("[login] failed:",ex);
            return ApiResponse.busionessError("账号密码错误");
        }
        return ApiResponse.ok(currentUser.getSession().getId());
    }
    @RequestMapping("/logout")
    public ApiResponse logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ApiResponse.ok("退出成功");
    }

    @RequestMapping("/info")
    @RequiresAuthentication
    public ApiResponse info() {
        Subject currentUser = SecurityUtils.getSubject();
        UserEntity userEntity= (UserEntity) currentUser.getPrincipal();
        UserBean userBean = userService.convertToBean(userEntity);
        return ApiResponse.ok(userBean);
    }

    @GetMapping("/401")
    public Object page401() {
        return ApiResponse.unlogin();
    }

    @GetMapping("/index")
    public Object pageIndex() {
        return ApiResponse.ok("欢迎");
    }

    @GetMapping("/403")
    public Object page403() {
        return ApiResponse.unauthz();
    }
}

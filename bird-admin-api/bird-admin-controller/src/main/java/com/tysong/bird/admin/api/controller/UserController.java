package com.tysong.bird.admin.api.controller;

import com.tysong.bird.admin.api.common.ApiResponse;
import com.tysong.bird.admin.dao.entity.UserEntity;
import com.tysong.bird.admin.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiresRoles(value={"admin"})
public class UserController extends BaseController{

    @Autowired
    UserService userService;

    @RequestMapping("/all")
    public ApiResponse all(){
        List<UserEntity> all = userService.findAll();
        return ApiResponse.ok(all);
    }

    @RequestMapping("/save")
    public ApiResponse save(@RequestBody UserEntity userEntityInput){
        if (userEntityInput.getDateAdd()==null){
            userEntityInput.setDateAdd(LocalDateTime.now());
        }
        if (userEntityInput.getDateUpdate()==null){
            userEntityInput.setDateUpdate(LocalDateTime.now());
        }
        userService.save(userEntityInput);
        return ApiResponse.ok("save success");
    }

    @RequestMapping("/deleteBatch")
    public ApiResponse deleteBatch(@RequestBody List<UserEntity> userEntityInputs){
        for (int i=0;i<userEntityInputs.size();i++) {
            userService.delete(userEntityInputs.get(i));
        }
        return ApiResponse.ok("delete success");
    }

    @RequestMapping("/delete")
    public ApiResponse delete(@RequestBody UserEntity userEntityInput){
        userService.delete(userEntityInput);
        return ApiResponse.ok("delete success");
    }
}

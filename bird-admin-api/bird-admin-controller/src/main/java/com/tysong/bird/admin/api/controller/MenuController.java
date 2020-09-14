package com.tysong.bird.admin.api.controller;

import com.tysong.bird.admin.api.common.ApiResponse;
import com.tysong.bird.admin.dao.entity.MenuEntity;
import com.tysong.bird.admin.dao.entity.UserEntity;
import com.tysong.bird.admin.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @RequestMapping("/my")
    public ApiResponse menu() {
        return ApiResponse.ok(menuService.getMenu());
    }

    @RequestMapping("/list")
    @RequiresRoles(value = "admin")
    public ApiResponse menuList() {
        List<MenuEntity> menuEntityList = menuService.findAll();
        return ApiResponse.ok(menuEntityList);
    }
    @RequestMapping("/save")
    public ApiResponse save(@RequestBody MenuEntity menuEntityInput){
        menuService.save(menuEntityInput);
        return ApiResponse.ok("save success");
    }
    @RequestMapping("/deleteBatch")
    public ApiResponse deleteBatch(@RequestBody List<MenuEntity> menuEntityList){
        for (int i=0;i<menuEntityList.size();i++) {
            menuService.delete(menuEntityList.get(i));
        }
        return ApiResponse.ok("delete success");
    }

    @RequestMapping("/delete")
    public ApiResponse delete(@RequestBody MenuEntity menuEntity){
        menuService.delete(menuEntity);
        return ApiResponse.ok("delete success");
    }
}

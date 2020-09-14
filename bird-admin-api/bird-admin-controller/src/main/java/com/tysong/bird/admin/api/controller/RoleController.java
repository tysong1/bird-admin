package com.tysong.bird.admin.api.controller;

import com.tysong.bird.admin.api.common.ApiResponse;
import com.tysong.bird.admin.dao.entity.RoleEntity;
import com.tysong.bird.admin.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/role")
@RequiresRoles(value={"admin"})
public class RoleController extends BaseController{

    @Autowired
    RoleService roleService;

    @RequestMapping("/all")
    public ApiResponse allRole(){
        List<RoleEntity> all = roleService.findAll();
        return ApiResponse.ok(all);
    }
    @RequestMapping("/save")
    public ApiResponse save(@RequestBody RoleEntity roleEntityInput){
        if (roleEntityInput.getDateAdd()==null){
            roleEntityInput.setDateAdd(LocalDateTime.now());
        }
        if (roleEntityInput.getDateUpdate()==null){
            roleEntityInput.setDateUpdate(LocalDateTime.now());
        }
        roleService.save(roleEntityInput);
        return ApiResponse.ok("save success");
    }

    @RequestMapping("/deleteBatch")
    public ApiResponse deleteBatch(@RequestBody List<RoleEntity> roleEntityInputs){
        for (int i=0;i<roleEntityInputs.size();i++) {
            roleService.delete(roleEntityInputs.get(i));
        }
        return ApiResponse.ok("delete success");
    }

    @RequestMapping("/delete")
    public ApiResponse delete(@RequestBody RoleEntity roleEntityInput){
        roleService.delete(roleEntityInput);
        return ApiResponse.ok("delete success");
    }
}

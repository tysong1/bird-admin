package com.tysong.bird.admin.api.controller;

import com.tysong.bird.admin.api.common.ApiResponse;
import com.tysong.bird.admin.common.bean.JvmBean;
import com.tysong.bird.admin.service.SystemMonitorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemMonitorController extends BaseController{
    @Autowired
    SystemMonitorService systemMonitorService;

    @RequestMapping("/monitor")
    @RequiresPermissions("admin:monitor:list")
    public ApiResponse monitor(){
        JvmBean jvmInfo = systemMonitorService.getJvmInfo();
        return ApiResponse.ok(jvmInfo);
    }
}

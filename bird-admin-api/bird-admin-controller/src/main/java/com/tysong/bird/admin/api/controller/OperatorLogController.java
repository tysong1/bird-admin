package com.tysong.bird.admin.api.controller;

import com.tysong.bird.admin.api.common.ApiResponse;
import com.tysong.bird.admin.service.OperatorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operatorLog")
public class OperatorLogController {
    @Autowired
    OperatorLogService operatorLogService;

    @RequestMapping("/all")
    public ApiResponse all(){
        return ApiResponse.ok(operatorLogService.findAll());
    }
}

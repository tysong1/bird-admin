package com.tysong.bird.admin.api.controller;

import com.tysong.bird.admin.api.common.ApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController extends BaseController{
    @RequestMapping("/hello")
    public ApiResponse hello(){
        return ApiResponse.ok("hello");
    }
}

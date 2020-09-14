package com.tysong.bird.admin.api.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ApiResponse handleException(RuntimeException ex){
        log.error("[ControllerExceptionHandler] failed:",ex);
        return ApiResponse.error(ex.getMessage());
    }
}

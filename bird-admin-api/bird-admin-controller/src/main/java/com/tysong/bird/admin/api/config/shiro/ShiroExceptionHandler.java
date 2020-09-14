package com.tysong.bird.admin.api.config.shiro;

import com.tysong.bird.admin.api.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ShiroExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public Object unauthenticatedHandler(AuthenticationException e) {
		log.error("[ShiroExceptionHandler] failed:",e);
		return ApiResponse.unlogin();
	}

	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	public Object unauthorizedHandler(AuthorizationException e) {
		log.error("[ShiroExceptionHandler] failed:",e);
		return ApiResponse.unauthz();
	}
}

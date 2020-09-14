package com.tysong.bird.admin.api.common;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApiResponse<T> {
    Integer code;
    String msg;
    T data;

    public ApiResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ApiResponse ok(Object data){
        return new ApiResponse(ApiCode.SUCCESS.getCode(),ApiCode.SUCCESS.getMessage(),data);
    }

    public static ApiResponse busionessError(Object data){
        return new ApiResponse(ApiCode.BUSIONESS_ERROR.getCode(),ApiCode.BUSIONESS_ERROR.getMessage(),data);
    }

    public static ApiResponse error(Object data){
        return new ApiResponse(ApiCode.BUSIONESS_ERROR.getCode(),ApiCode.BUSIONESS_ERROR.getMessage(),data);
    }

    public static ApiResponse unlogin() {
        return new ApiResponse(ApiCode.UNLOGIN.getCode(),ApiCode.UNLOGIN.getMessage(),null);
    }

    public static ApiResponse unauthz() {
        return new ApiResponse(ApiCode.UNAUTH.getCode(),ApiCode.UNAUTH.getMessage(),null);
    }
}

package com.tysong.bird.admin.api.common;

import lombok.Getter;

@Getter
public enum ApiCode {
    SUCCESS(0, "success"),
    ERROR(1, "error"),
    UNLOGIN(2, "unLogin"),
    UNAUTH(3, "unAuth"),
    BUSIONESS_ERROR(4, "businessError");
    Integer code;
    String message;

    ApiCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

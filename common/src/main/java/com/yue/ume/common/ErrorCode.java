package com.yue.ume.common;

/**
 * @description: 错误码定义
 * @author yueyue
 * @date 2023/4/17 11:04
 * @version 1.0
 */
public enum ErrorCode {
    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    TOO_MANY_REQUEST(42900, "请求过于频繁"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");


    public int getCode() {
        return code;
    }   


    public String getMessage() {
        return message;
    }

    private final int code;
    private final String message;

    /**
     * @description: aaaa
     * @param: code
     * @param: message
     * @return: aaaa
     * @author yueyue
     * @date: 2023/4/17 11:25
     */
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

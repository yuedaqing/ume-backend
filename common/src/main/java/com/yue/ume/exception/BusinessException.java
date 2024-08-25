package com.yue.ume.exception;

import com.yue.ume.common.ErrorCode;

/**
 * @author yueyue
 * @version 1.0
 * @description: 自定义异常类
 * @date 2023/4/17 11:37
 */
public class BusinessException extends RuntimeException {

    public int getCode() {
        return code;
    }

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

}

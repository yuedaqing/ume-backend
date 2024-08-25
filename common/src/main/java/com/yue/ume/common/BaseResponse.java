package com.yue.ume.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yueyue
 * @version 1.0
 * @description: 通用返回类
 * @date 2023/4/17 11:30
 */
@Data
public class BaseResponse<T> implements Serializable {
    public static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public BaseResponse() {

    }

    public BaseResponse(int code, T data) {

        this(code,  data,"");
    }
    public BaseResponse(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }

    public BaseResponse(Integer code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

package com.yue.ume.common;

/**
 * @author yueyue
 * @version 1.0
 * @description: 异常工具类
 * @date 2023/4/17 11:43
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    public static BaseResponse<?> success() {
        return new BaseResponse<>(0, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 状态码
     * @return 返回包装数据
     */
    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code    状态码
     * @param message 消息
     * @return 返回包装数据
     */
    public static BaseResponse<?> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 状态码
     * @return 返回包装数据
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), message, null);
    }
}

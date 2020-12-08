package com.ycr.common.api;

import lombok.Data;

/**
 * @author ycr
 * @date 2020/10/19
 */
@Data
public class CommonResult<T> {

    private long code;

    private boolean status;

    private String message;

    private T data;

    private CommonResult() {
    }

    private CommonResult(T data) {
        this.data = data;
    }

    private CommonResult(T data, long code, String message, boolean status) {
        this.data = data;
        this.code = code;
        this.status = status;
        this.message = message;
    }



    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(data, ResultCode.SUCCESS.getCode(),  ResultCode.SUCCESS.getMessage(), true);
    }

    public static <T> CommonResult<T> fail() {
        return new CommonResult<>(null, ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage(), false);
    }

    public static <T> CommonResult<T> fail(ResultCode code) {
        return new CommonResult<>(null, code.getCode(), code.getMessage(), false);
    }


}

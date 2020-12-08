package com.ycr.common.exception;

import com.ycr.common.api.ResultCode;

/**
 * @author ycr
 * @date 2020/11/4
 */
public class BusinessException extends RuntimeException {

        /*错误编码*/
        private ResultCode code;

        /*异常信息*/
        private String message;

        public BusinessException(ResultCode code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public ResultCode getCode() {
            return code;
        }

}

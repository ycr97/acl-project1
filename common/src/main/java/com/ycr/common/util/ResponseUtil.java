package com.ycr.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ycr.common.api.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ycr
 * @date 2020/11/25
 */
public class ResponseUtil {

    public static void out(HttpServletResponse response, CommonResult<String> commonResult) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), commonResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

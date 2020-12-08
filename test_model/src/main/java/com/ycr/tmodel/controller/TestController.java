package com.ycr.tmodel.controller;

import com.ycr.common.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycr
 * @date 2020/12/3
 */
@RestController
@RequestMapping("/test")
public class TestController{

    @GetMapping
    public CommonResult<String> getTestData() {
        return CommonResult.success("test model...");
    }
}

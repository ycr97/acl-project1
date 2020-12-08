package com.ycr.acl.controller;


import com.ycr.acl.api.TestModelApi;
import com.ycr.common.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ycr
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private TestModelApi testModelApi;

    @GetMapping
    public CommonResult<String> test() {
        String testData = testModelApi.getTestData().getData();
        return CommonResult.success("user model 调用: " + testData);
    }
}


package com.ycr.acl.api;

import com.ycr.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ycr
 * @date 2020/12/3
 */
@FeignClient("test-model")
@Component
public interface TestModelApi {

    @GetMapping("/test")
    CommonResult<String> getTestData();
}

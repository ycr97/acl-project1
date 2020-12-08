package com.ycr.acl.controller;


import com.ycr.acl.model.PermissionVO;
import com.ycr.acl.service.PermissionService;
import com.ycr.common.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author ycr
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @GetMapping("/all")
    public CommonResult<List<PermissionVO>> getAllPermission() {
        return CommonResult.success(permissionService.getAllPermission());
    }

    @DeleteMapping("/{id}")
    public CommonResult<Boolean> deletePermissionById(@PathVariable("id") String id) {
        permissionService.removePermissionById(id);
        return CommonResult.success(true);
    }
}


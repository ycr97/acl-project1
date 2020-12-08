package com.ycr.acl.service;

import com.ycr.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycr.acl.model.PermissionVO;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author ycr
 * @since 2020-11-27
 */
public interface PermissionService extends IService<Permission> {

    List<PermissionVO> getAllPermission();

    void removePermissionById(String id);

    List<String> getPermissionByUserId(String id);
}

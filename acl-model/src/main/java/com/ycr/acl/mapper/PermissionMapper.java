package com.ycr.acl.mapper;

import com.ycr.acl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author ycr
 * @since 2020-11-27
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> getAllPermission();

    void deletePermissionByIds(@Param("idList") List<String> idList);

    List<String> getChildrenIdList(String id);

    List<String> getAllPermissionValue();

    List<String> getPermissionValueByUserId(@Param("id") String id);
}

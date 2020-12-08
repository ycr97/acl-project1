package com.ycr.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycr.acl.constant.AclConstant;
import com.ycr.acl.entity.Permission;
import com.ycr.acl.entity.User;
import com.ycr.acl.mapper.PermissionMapper;
import com.ycr.acl.model.PermissionVO;
import com.ycr.acl.service.PermissionService;
import com.ycr.acl.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author ycr
 * @since 2020-11-27
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private UserService userService;

    /**
     * 按照树形结构得到权限
     *
     * @return permission list
     */
    @Override
    public List<PermissionVO> getAllPermission() {
        List<Permission> allPermission = permissionMapper.getAllPermission();
        return this.buildPermission(allPermission);
    }

    private List<PermissionVO> buildPermission(List<Permission> allPermission) {
        List<PermissionVO> returnList = new ArrayList<>();
        allPermission.stream()
                .filter(temp -> "0".equals(temp.getPid()))
                .forEach(temp -> returnList.add(getChildrenList(temp, allPermission)));
        return returnList;
    }

    private PermissionVO getChildrenList(Permission parent, List<Permission> allPermission) {
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(parent, vo);

        List<PermissionVO> children = allPermission.stream()
                .filter(temp -> temp.getPid().equals(parent.getId()))
                .map(temp -> getChildrenList(temp, allPermission))
                .collect(Collectors.toList());
        vo.setChildren(children);
        return vo;
    }


    /**
     * 删除权限极其子节点
     *
     * @param id id
     */
    @Override
    public void removePermissionById(String id) {
        List<String> idList = new ArrayList<>();
        idList.add(id);
        this.getChildrenIds(id, idList);
        permissionMapper.deletePermissionByIds(idList);
    }

    @Override
    public List<String> getPermissionByUserId(String id) {
        List<String> permissionList = null;
        if (this.userIsAdmin(id)) {
            permissionList = permissionMapper.getAllPermissionValue();
        } else {
            permissionList = permissionMapper.getPermissionValueByUserId(id);
        }
        return permissionList;
    }

    private boolean userIsAdmin(String id) {
        User user = userService.getUserById(id);
        if (user != null && AclConstant.CONSTANT_ADMIN.equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    private void getChildrenIds(String id, List<String> idList) {
        List<String> childrenIdList = permissionMapper.getChildrenIdList(id);
        childrenIdList.forEach(temp -> {
            // 封装到list中
            idList.add(temp);
            // 递归查询子节点
            getChildrenIds(temp, idList);
        });
    }


}

package com.ycr.acl.service.impl;

import com.ycr.acl.entity.User;
import com.ycr.acl.service.PermissionService;
import com.ycr.acl.service.UserService;
import com.ycr.common.api.ResultCode;
import com.ycr.common.exception.BusinessException;
import com.ycr.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author ycr
 * @date 2020/11/30
 */

@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectUserByName(username);
        if (user == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "没有该用户");
        }
        List<String> permissionList = permissionService.getPermissionByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser();
        com.ycr.security.entity.User user1 = new com.ycr.security.entity.User();
        BeanUtils.copyProperties(user, user1);
        securityUser.setPermissionList(permissionList);
        securityUser.setCurrentUser(user1);
        return securityUser;
    }
}


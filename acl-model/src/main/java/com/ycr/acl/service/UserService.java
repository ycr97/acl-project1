package com.ycr.acl.service;

import com.ycr.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ycr
 * @since 2020-11-27
 */
public interface UserService extends IService<User> {

    User getUserById(String id);

    User selectUserByName(String username);
}

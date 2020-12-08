package com.ycr.security.util;

import cn.hutool.crypto.SecureUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ycr
 * @date 2020/11/25
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    /**
     * 进行MD5加密
     *
     * @param charSequence
     * @return
     */
    @Override
    public String encode(CharSequence charSequence) {
        return SecureUtil.md5(charSequence.toString());
    }

    /**
     * 进行密码比对
     *
     * @param charSequence
     * @param encodePassword
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String encodePassword) {
        return encodePassword.equals(SecureUtil.md5(charSequence.toString()));
    }
}

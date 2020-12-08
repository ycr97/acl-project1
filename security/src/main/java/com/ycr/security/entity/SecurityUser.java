package com.ycr.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ycr
 * @date 2020/11/25
 */
@Data
public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = -7465651945888961300L;
    /**
     * 当前登录用户
     **/
    private transient User currentUser;

    /**
     * 当前权限
     **/
    private List<String> permissionList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String temp : permissionList) {
            if (StringUtils.isEmpty(temp)) {
                continue;
            }
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(temp);
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return currentUser.getPassword();
    }

    @Override
    public String getUsername() {
        return currentUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

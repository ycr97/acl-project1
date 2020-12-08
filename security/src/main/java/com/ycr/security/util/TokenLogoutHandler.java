package com.ycr.security.util;


import com.ycr.common.api.CommonResult;
import com.ycr.common.util.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ycr
 * @date 2020/11/25
 */
@Component
public class TokenLogoutHandler implements LogoutHandler {

    private RedisTemplate redisTemplate;

    private TokenManager tokenManager;

    public TokenLogoutHandler(RedisTemplate redisTemplate, TokenManager tokenManager) {
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (token != null) {
            String username = tokenManager.getUserInfoFromToken(token);
            redisTemplate.delete(username);
        }
        ResponseUtil.out(response, CommonResult.success("退出成功"));

    }
}

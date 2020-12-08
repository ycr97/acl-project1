package com.ycr.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ycr.common.api.CommonResult;
import com.ycr.common.api.ResultCode;
import com.ycr.common.util.ResponseUtil;
import com.ycr.security.entity.SecurityUser;
import com.ycr.security.entity.User;
import com.ycr.security.util.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author ycr
 * @date 2020/11/25
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private TokenManager tokenManager;

    private RedisTemplate redisTemplate;

    //    @Resource
    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(TokenManager tokenManager, RedisTemplate redisTemplate, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login", "POST"));
    }

    /**
     * 获取表单提交的用户名和密码
     *
     * @param request  请求对象
     * @param response 响应对象
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("认证失败");
        }
    }

    /**
     * 认证成功会调用的方法
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 认证成功,得到认证成功的用户信息
        SecurityUser user = (SecurityUser) authResult.getPrincipal();
        // 根据用户信息生成
        String token = tokenManager.createToken(user.getCurrentUser().getUsername());
        // 把用户名名称和权限列表放到Redis
        redisTemplate.opsForValue().set(user.getUsername(), user.getAuthorities());
        //返回token
        ResponseUtil.out(response, CommonResult.success(token));
    }

    /**
     * 认证失败会调用的方法
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, CommonResult.fail(ResultCode.VALIDATE_FAILED));
    }
}

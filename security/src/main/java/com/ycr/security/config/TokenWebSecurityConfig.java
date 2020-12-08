package com.ycr.security.config;


import com.ycr.security.filter.TokenAuthenticationFilter;
import com.ycr.security.filter.TokenLoginFilter;
import com.ycr.security.util.DefaultPasswordEncoder;
import com.ycr.security.util.TokenLogoutHandler;
import com.ycr.security.util.TokenManager;
import com.ycr.security.util.UnAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author ycr
 * @date 2020/11/26
 */
@Configuration
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenManager tokenManager;

    private RedisTemplate redisTemplate;

    private DefaultPasswordEncoder defaultPasswordEncoder;

    private UserDetailsService userDetailsService;

    @Autowired
    public TokenWebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  RedisTemplate redisTemplate, TokenManager tokenManager) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                // 没有权限访问
                .authenticationEntryPoint(new UnAuthEntryPoint())
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .logout().logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new TokenLogoutHandler(redisTemplate, tokenManager)).and()
                .addFilter(new TokenLoginFilter(tokenManager, redisTemplate, authenticationManager()))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**");
    }
}

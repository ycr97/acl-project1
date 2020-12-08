package com.ycr.security.util;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ycr
 * @date 2020/11/25
 */
@Component
public class TokenManager {

    // 过期时长 分钟
    private long expirationTime = 30 * 60 * 100;

    // 秘钥
    private String tokenSignKey = "123456";

    /**
     * 根据用户名生成Token字符串
     *
     * @param username 用户名
     * @return token
     */
    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }



    /**
     * 根据Token字符串得到用户信息
     * @param token
     * @return
     */
    public String getUserInfoFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

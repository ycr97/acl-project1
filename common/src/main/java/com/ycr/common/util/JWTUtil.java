package com.ycr.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author ycr
 * @date 2020/11/10
 */
public class JWTUtil {

    private static final String APP_SECRET = "waskjdSaHKxSSS";

    private static Key getKeyInstance() {
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        byte[] bytes = DatatypeConverter.parseBase64Binary(APP_SECRET);
        return new SecretKeySpec(bytes, algorithm.getJcaName());
    }

    /**
     * 获取token
     *
     * @param info   UserInfo
     * @param expire 过期时间 单位秒
     * @return jwtToken
     */
    public static String getJwtToken(JWTInfo info, int expire) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("dream-team")
                .setIssuedAt(new Date())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .claim("id", info.getId())
                .claim("username", info.getUsername())
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
    }

    /**
     * 检查token是否存在和过期
     *
     * @param jwtToken token
     * @return true or false
     */
    public static boolean checkJwtToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(getKeyInstance()).parse(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 检查token是否存在和过期
     * @param request 请求对象
     * @return true or false
     */
    public static boolean checkJwtToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(getKeyInstance()).parse(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 从token中取出信息
     * @param jwtToken token
     * @return jwtInfo
     */
    public static JWTInfo getUserInfoByJwtToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return new JWTInfo(claims.get("id").toString(), claims.get("username").toString());
    }

    /**
     * 从token中取出信息
     * @param request 请求对象
     * @return jwtInfo
     */
    public static JWTInfo getUserInfoByJwtToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        return getUserInfoByJwtToken(token);
    }

}

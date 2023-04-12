package cc.seckill.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * description: JwtUtil <br>
 * date: 2022/7/16 19:52 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public class JwtUtil {

    private static final String JWT_SECURITY_KEY;

    // 默认过期时间: 7天
    private static final long DEFAULT_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    static {
        JWT_SECURITY_KEY = System.getenv("jwt_security_key");
        if (JWT_SECURITY_KEY == null) {
            throw new RuntimeException("jwt的密钥不能为空!");
        }
    }

    public static String createJWT(String username) {
        return createJWT(username, null, DEFAULT_EXPIRE_TIME);
    }

    public static String createJWT(String username, String subject) {
        return createJWT(username, subject, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定JWT_SEC秘钥
     */
    public static String createJWT(String username, String subject, long ttlMillis) {
        // 指定签名的时候使用的签名算法，也就是header那部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建payload的私有声明（根据特定的业务需要添加）
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        // 添加payload声明
        // 设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                // iat: jwt的签发时间
                .setIssuedAt(now)
                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, JWT_SECURITY_KEY.getBytes(StandardCharsets.UTF_8));
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            // 设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    /**
     * Token的解密
     *
     * @param token 加密后的token
     * @return
     */
    public static Claims parseJWT(String token) {
        // 得到DefaultJwtParser
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(JWT_SECURITY_KEY.getBytes(StandardCharsets.UTF_8))
                // 设置需要解析的jwt
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * description: parseJWTifNotExpired 如果token未过期则解析,否则返回null<br>
     * version: 1.0 <br>
     * date: 2022/12/11 14:35 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param token 令牌
     * @return io.jsonwebtoken.Claims
     */
    public static Claims parseJWTifNotExpired(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        return parseJWT(token);
    }


    /**
     * description: isExpired 判断token是否过期<br>
     * version: 1.0 <br>
     * date: 2022/12/11 14:35 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param token 令牌
     * @return boolean
     */
    public static boolean isExpired(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        try {
            parseJWT(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

}

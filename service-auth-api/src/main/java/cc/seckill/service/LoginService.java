package cc.seckill.service;

import cc.seckill.domain.Result;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: LoginService <br>
 * date: 2022/12/11 11:23 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public interface LoginService {

    // 默认redis前缀
    String LOGIN_TOKEN_REDIS_KEY_PREFIX = "LOGIN_TOKEN:";


    // 默认key过期时间,单位秒,默认7天过期
    long DEFAULT_REDIS_LOGIN_TIMEOUT = 60 * 60 * 24 * 7;


    /**
     * description: login 用户账户/邮箱/手机号 + 密码 登录<br>
     * version: 1.0 <br>
     * date: 2022/12/22 19:54 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param id       用户的唯一id
     * @param password 用户密码
     * @return cc.seckill.domain.Result
     */
    Result login(String id, String password);


    /**
     * description: loginByVerifyCode 根据手机号和验证码登录 <br>
     * version: 1.0 <br>
     * date: 2022/12/22 19:56 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
     * @return cc.seckill.domain.Result
     */
    Result loginByVerifyCode(String phoneNumber, String verifyCode);


    /**
     * description: logout 登出<br>
     * version: 1.0 <br>
     * date: 2022/12/28 11:38 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @return cc.seckill.domain.Result
     */
    Result logout();

    /**
     * description: logout 登出<br>
     * version: 1.0 <br>
     * date: 2023/2/3 13:49 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param request  request
     * @param response response
     * @return cc.seckill.domain.Result
     */
    Result logout(HttpServletRequest request, HttpServletResponse response);


    static String getKeyForRedis(String username) {
        return LOGIN_TOKEN_REDIS_KEY_PREFIX + username;
    }

    ;

    static String getKeyForRedis(Claims claims) {
        return getKeyForRedis(claims.get("username").toString());
    }

    ;
}

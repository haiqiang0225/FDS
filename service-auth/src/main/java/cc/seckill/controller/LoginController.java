package cc.seckill.controller;

import cc.seckill.domain.Result;
import cc.seckill.enums.ResultStatusCodes;
import cc.seckill.exceptions.GlobalException;
import cc.seckill.service.LoginService;
import cc.seckill.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * description: LoginController <br>
 * date: 2022/12/10 23:23 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@RestController
@Slf4j
@Validated
public class LoginController {

    // 用户名匹配正则,可以是username/电话/邮箱
    public static final String ID_REGEX
            = "^("
            + UserService.USERNAME_REGEX + "|"
            + UserService.EMAIL_REGEX + "|"
            + UserService.PHONE_REGEX + "|"
            + ")$";


    // 验证码正则, 数字,6位
    public static final String VERIFY_CODE_REGEX = "^(\\s|\\d{6})$";
    @Resource
    private LoginService loginService;

    /**
     * description: login 用户登录, 支持账户密码登录/验证码登录<br>
     * version: 1.0 <br>
     * date: 2022/12/29 21:33 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param id         用户id,可以是username/电话/邮箱
     * @param password   用户密码
     * @param verifyCode 验证码
     * @return cc.seckill.domain.Result
     */
    @RequestMapping("/login")
    public Result login(
            @NotNull @Pattern(regexp = ID_REGEX, message = "账号格式错误")
            @RequestParam(value = "username") String id,
            @Pattern(regexp = UserService.PASS_REGEX, message = "密码格式错误!")
            @RequestParam(value = "password", required = false) String password,
            @Pattern(regexp = VERIFY_CODE_REGEX, message = "验证码格式错误!")
            @RequestParam(value = "verifyCode", required = false) String verifyCode) {

        // 使用验证码登录
        if (StringUtils.hasText(verifyCode)) {
            log.info("使用验证码登录, 手机号: {}, 验证码: {}", id, verifyCode);
            return loginService.loginByVerifyCode(id, verifyCode);
        } else if (StringUtils.hasText(password)) {
            log.info("使用账号密码登录, 账号: {}", id);
            return loginService.login(id, password);
        } else {
            throw new GlobalException(ResultStatusCodes.INVALID_PARAM);
        }
    }

    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("用户登出");
        return loginService.logout(request, response);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @RequestMapping("/test")
    public Result test() {
        return Result.ok("好的!");
    }

    @PreAuthorize("hasAnyRole('admin2')")
    @RequestMapping("/test2")
    public Result test2() {
        return Result.ok("歪日!");
    }
}

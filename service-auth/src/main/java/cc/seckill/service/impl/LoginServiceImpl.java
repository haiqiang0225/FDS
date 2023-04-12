package cc.seckill.service.impl;

import cc.seckill.domain.Result;
import cc.seckill.entities.Menu;
import cc.seckill.entities.SysUser;
import cc.seckill.enums.ResultStatusCodes;
import cc.seckill.exceptions.GlobalException;
import cc.seckill.service.LoginService;
import cc.seckill.service.MenuService;
import cc.seckill.service.UserService;
import cc.seckill.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * description: LoginServiceImpl <br>
 * date: 2022/12/11 11:23 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 *
 * @see DaoAuthenticationProvider
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private MenuService menuService;

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;


    @Override
    public Result login(String id, String password) {
        // todo: 判断用户id的类型,调用不同的登录方法
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        // 这里会验证密码, 源码在DaoAuthenticationProvider#additionalAuthenticationChecks 中
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication)) {
            throw new GlobalException(ResultStatusCodes.WRONG_USERNAME_OR_PASS);
        }
        User user = (User) authentication.getPrincipal();
        log.info("用户: {}, 已登陆", user.getUsername());

        // 数据准备
        String jwt = JwtUtil.createJWT(user.getUsername());
        List<Menu> menuList = menuService.resolveMenuTreeListByUsername(user.getUsername());

        // 尝试从请求上下文获取
        SysUser sysUser = (SysUser) RequestContextHolder.currentRequestAttributes().getAttribute("user",
                RequestAttributes.SCOPE_SESSION);
        log.debug("从RequestContextHolder中获取用户信息: {}", sysUser);

        // 如果没有成功从上下文中获取到,则查数据库
        if (Objects.isNull(sysUser)) {
            sysUser = userService.getByUsername(user.getUsername());
        }
        // 如果查了数据库仍为空, 报异常
        if (Objects.isNull(sysUser)) {
            throw new GlobalException(ResultStatusCodes.LOGIN_FAIL);
        }

        // token 存到redis
        stringRedisTemplate.opsForValue().set(LoginService.getKeyForRedis(user.getUsername()), jwt,
                DEFAULT_REDIS_LOGIN_TIMEOUT, TimeUnit.SECONDS);

        // 设置响应数据
        Result result = Result.ok();
        result.put("token", jwt);
        result.put("menuList", menuList);
        result.put("user", sysUser);
        return result;
    }

    @Override
    public Result loginByVerifyCode(String phoneNumber, String verifyCode) {
        return null;
    }

    @Override
    public Result logout() {
        return logout(request, response);
    }

    @Override
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应头跳转
        response.addHeader("REDIRECT", "REDIRECT");
        response.addHeader("LOCATION", "/login");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // 清除登录状态
//        stringRedisTemplate.opsForValue().getOperations().delete(LoginService.getKeyForRedis(authentication
//        .getPrincipal().toString()));
//        SecurityContextHolder.clearContext();

        return Result.ok("登出成功!");
    }

}

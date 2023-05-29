package cc.seckill.service.impl;

import cc.seckill.entities.SysUser;
import cc.seckill.enums.ResultStatusCodes;
import cc.seckill.enums.UserEnum;
import cc.seckill.exceptions.GlobalException;
import cc.seckill.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;

/**
 * description: UserDetailsServiceImpl <br>
 * date: 2022/12/10 20:46 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        SysUser user = userService.getByUsername(username);
        if (Objects.isNull(user)) {
            throw new GlobalException(ResultStatusCodes.WRONG_USERNAME_OR_PASS);
        }
        // 如果用户账户的状态不是正常,则禁止登录
        if (!UserEnum.STATUS_NORMAL.match(user.getStatus())) {
            throw new GlobalException(ResultStatusCodes.USER_NOT_AVAILABLE);
        }

        RequestContextHolder.currentRequestAttributes().setAttribute("user", user, RequestAttributes.SCOPE_SESSION);
        // 获取用户权限
        Collection<? extends GrantedAuthority> userAuthorities =
                userService.getUserAuthoritiesByUsername(username);
        return new User(user.getUsername(), user.getPassword(), userAuthorities);
    }


}

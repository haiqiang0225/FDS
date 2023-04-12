package cc.seckill.service.impl;

import cc.seckill.domain.Result;
import cc.seckill.entities.Menu;
import cc.seckill.entities.Role;
import cc.seckill.entities.SysUser;
import cc.seckill.mapper.MenuMapper;
import cc.seckill.mapper.RoleMapper;
import cc.seckill.mapper.UserMapper;
import cc.seckill.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * description: UserServiceImpl <br>
 * date: 2022/12/15 14:49 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuMapper menuMapper;


    @Override
    public SysUser getByUsername(String username) {
        log.info("根据id查询用户: {}", username);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public String getUserAuthorityString(String username) {
        StringBuilder authorityString = new StringBuilder();

        // 1. 获取所有角色信息
        List<Role> roleList = roleMapper.selectRolesByUsername(username);

        List<String> roleStrList = roleList.stream()
                .map(role -> "ROLE_" + role.getRoleName())
                .toList();

        // 将角色信息拼装
        if (!roleList.isEmpty()) {
            String joinedStr = String.join(",", roleStrList);
            authorityString.append(joinedStr);
        }

        // 2. 根据角色信息获取所有权限信息,由于不同角色可能有相同权限,用Set去除相同的权限
        Set<String> permsSet = new HashSet<>();
        for (Role role : roleList) {
            List<Menu> menuList = menuMapper.selectMenuListByRoleName(role.getRoleName());
            // 获取所有权限字符,添加到set中
            menuList.stream()
                    .map(Menu::getPerms)
                    .filter(StringUtils::hasText)
                    .forEach(permsSet::add);
        }

        if (!permsSet.isEmpty()) {
            authorityString.append(",");
            String joinedMenuString = String.join(",", permsSet);
            authorityString.append(joinedMenuString);
        }

        log.debug("用户权限: {}", authorityString);
        return authorityString.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getUserAuthoritiesByUsername(String username) {
        //todo: 性能优化 => 获取权限是经常的操作,加带过期时间的本地缓存
        String userAuthorityString = getUserAuthorityString(username);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userAuthorityString);
    }

    @Override
    public UserLoginType resolveUserLoginType(String id) {

        return null;
    }

    @Override
    public Result saveUserInfo(SysUser sysUser) {
        // 1. 查询一下是否是当前登录用户, 或者拥有修改用户信息的权限
        // 先判断是否是当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (sysUser.getUsername() != null && sysUser.getUsername().equals(principal)) {
            log.info("用户自行修改用户信息为: {}", sysUser);
            userMapper.updateById(sysUser);
        } else {
            // 如果不是再判断是否有权限
            // 如果都不是,那么说明操作非法,抛出异常
            saveUserInfoByAuthorized(sysUser);
        }
        return Result.ok();
    }

    @Override
    public Result resetPassword(SysUser sysUser, String oldPassword) {
        return null;
    }

    @Override
    public Result resetPasswordById(Long userId, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public Result resetPasswordByUsername(String username, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public Result resetPasswordByVerifyCode(SysUser sysUser, String verifyCode) {
        return null;
    }

    @PreAuthorize("hasAnyAuthority('system:all', 'system:user:all', 'system:user:edit')")
    public void saveUserInfoByAuthorized(SysUser sysUser) {
        userMapper.updateById(sysUser);
    }
}

package cc.seckill.service;

import cc.seckill.domain.Result;
import cc.seckill.entities.Role;
import cc.seckill.entities.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * description: UserService <br>
 * date: 2022/12/15 14:49 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public interface UserService {

    public static final String USERNAME_REGEX = "^[a-zA-z]\\w{3,15}$";


    public static final String EMAIL_REGEX = "^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$";

    public static final String PHONE_REGEX = "^0\\d{2,3}-?\\d{7,8}$";


    // 密码验证正则,至少6位,包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符"!@#$%^&*?"。
    public static final String PASS_REGEX = "^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*?]).*$";

    /**
     * description: getByUsername 根据用户名查找用户<br>
     * version: 1.0 <br>
     * date: 2022/12/28 21:35 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param username 用户名
     * @return cc.seckill.entities.SysUser
     */
    SysUser getByUsername(String username);

    /**
     * description: getUserAuthorityString 根据用户查询用户权限信息,
     * 格式为: ROLE_A, ROLE_B,  ..., perm_a, perm_b,...<br>
     * version: 1.0 <br>
     * date: 2022/12/28 21:36 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param username 用户名
     * @return java.lang.String
     */
    String getUserAuthorityString(String username);

    /**
     * description: getUserAuthoritiesByUsername 获取用户权限列表<br>
     * version: 1.0 <br>
     * date: 2022/12/29 11:27 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param username 用户名
     * @return java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     */
    Collection<? extends GrantedAuthority> getUserAuthoritiesByUsername(String username);

    /**
     * description: resolveUserLoginType 解析用户登录方式<br>
     * version: 1.0 <br>
     * date: 2022/12/29 21:24 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param id 传入的id
     * @return cc.seckill.service.UserService.UserLoginType
     */
    UserLoginType resolveUserLoginType(String id);

    /**
     * description: saveUserInfo <br>
     * version: 1.0 <br>
     * date: 2023/3/3 13:41 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param sysUser 系统用户
     * @return cc.seckill.domain.Result
     */
    Result saveUserInfo(SysUser sysUser);

    /**
     * description: resetPassword 重置密码<br>
     * version: 1.0 <br>
     * date: 2023/3/4 21:50 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param sysUser     包含新密码的SysUser实例
     * @param oldPassword 旧密码
     * @return cc.seckill.domain.Result
     */
    Result resetPassword(SysUser sysUser, String oldPassword);

    /**
     * description: resetPasswordById 根据ID重置密码<br>
     * version: 1.0 <br>
     * date: 2023/3/4 21:52 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return cc.seckill.domain.Result
     */
    Result resetPasswordById(Long userId, String oldPassword, String newPassword);


    /**
     * description: resetPasswordByUsername  根据用户名重置密码<br>
     * version: 1.0 <br>
     * date: 2023/3/4 21:52 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param username    用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return cc.seckill.domain.Result
     */
    Result resetPasswordByUsername(String username, String oldPassword, String newPassword);

    /**
     * description: resetPasswordByVerifyCode 根据验证码重置密码<br>
     * version: 1.0 <br>
     * date: 2023/3/4 21:54 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param sysUser    SysUser实例
     * @param verifyCode 验证码
     * @return cc.seckill.domain.Result
     */
    Result resetPasswordByVerifyCode(SysUser sysUser, String verifyCode);

    /**
     * description: getRoleList 获取角色列表 <br>
     * version: 1.0 <br>
     * date: 2023/4/16 19:44 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param username 用户名
     * @param pageNum  页数
     * @param pageSize 页大小
     * @return java.util.List<cc.seckill.entities.SysUser>
     */
    List<SysUser> getUserListByUsername(String username, Integer pageNum, Integer pageSize);

    /**
     * description: getRoleCount 获取总数 <br>
     * version: 1.0 <br>
     * date: 2023/4/16 19:44 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param query 查询
     * @return java.lang.Long
     */
    Long getUserCount(String query);

    /**
     * description: getRolesForUserByName <br>
     * version: 1.0 <br>
     * date: 2023/4/17 08:33 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param username 用户名
     * @param start    起始
     * @param count    偏移
     * @return java.util.List<cc.seckill.entities.Role>
     */
    List<Role> getRolesForUserByName(String username, Integer start, Integer count);

    boolean updateRolesForUser(SysUser sysUser, List<Role> roleList);

    /**
     * description:  支持的用户登录方式枚举 <br>
     * version: 1.0 <br>
     * date: 2022/12/29 21:23 <br>
     * author: haiqiang0225@gmail.com <br>
     */
    enum UserLoginType {
        USERNAME,
        EMAIL,
        PHONE;
    }

}

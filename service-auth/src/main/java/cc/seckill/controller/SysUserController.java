package cc.seckill.controller;

import cc.seckill.domain.Result;
import cc.seckill.entities.Role;
import cc.seckill.entities.SysUser;
import cc.seckill.enums.ResultStatusCodes;
import cc.seckill.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * description: SysUserController <br>
 * date: 2023/3/3 13:31 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {

    @Resource
    private UserService userService;

    @RequestMapping("/save")
    public Result save(SysUser sysUser) {
        log.info("{}", sysUser);
        return userService.saveUserInfo(sysUser);
    }


    @RequestMapping("/updateUserPwd")
    public Result updateUserPwd(@RequestParam("oldPassword") String oldPassword,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("userId") Long userId,
                                @RequestParam("username") String username) {

        log.info("{}, {}", oldPassword, newPassword);
        log.info("{}, {}", userId, username);
        Result result = null;
        // 如果有用户名则根据用户名更新, 否则根据id
        if (StringUtils.hasText(username)) {
            result = userService.resetPasswordByUsername(username, oldPassword, newPassword);
        } else {
            result = userService.resetPasswordById(userId, oldPassword, newPassword);
        }
        if (Objects.isNull(result)) {
            ResultStatusCodes modifyFail = ResultStatusCodes.MODIFY_FAIL;
            return Result.error(modifyFail.getCode(), modifyFail.getMessage());
        }
        return result;
    }

    @RequestMapping("/list")
    public Result getUserList(@RequestParam("query") String query,
                              @RequestParam("pageNum") Integer pageNum,
                              @RequestParam("pageSize") Integer pageSize) {
        List<SysUser> roleList = userService.getUserListByUsername(query, pageNum, pageSize);
        Long totalCount = userService.getUserCount(query);
        log.info("查询用户列表, 参数: query = {} , pageNum = {}, pageSize = {}", query, pageNum, pageSize);
        Result result = Result.ok();
        result.put("userList", roleList);
        result.put("total", totalCount);
        return result;
    }


    @RequestMapping("/roles")
    public Result getRoleList(SysUser sysUser) {
        log.info("sysUser = {}", sysUser);
        Result result = new Result();
        if (sysUser.getUsername() != null && StringUtils.hasText(sysUser.getUsername())) {
            List<Role> roles = userService.getRolesForUserByName(sysUser.getUsername(), 0, -1);
            result.put("roleList", roles);
        }
        return result;
    }


    @RequestMapping("/updateRoles")
    public Result updateRoles(SysUser sysUser, @RequestBody List<Role> roleList) {
        log.info("sysUser = {}", sysUser);
        log.info("roleList = {}", roleList);
        Result result = new Result();
        boolean ok = userService.updateRolesForUser(sysUser, roleList);
        if (!ok) {
            result = Result.error();
        }
        return result;
    }

}

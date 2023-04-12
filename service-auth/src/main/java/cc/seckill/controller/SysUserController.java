package cc.seckill.controller;

import cc.seckill.domain.Result;
import cc.seckill.entities.SysUser;
import cc.seckill.enums.ResultStatusCodes;
import cc.seckill.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
}

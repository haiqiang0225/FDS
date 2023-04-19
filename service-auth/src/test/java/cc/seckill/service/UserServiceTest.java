package cc.seckill.service;

import cc.seckill.AuthMainApp;
import cc.seckill.entities.SysUser;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * description: UserServiceTest <br>
 * date: 2022/12/29 11:11 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {AuthMainApp.class})
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeAll
    public static void setup() {
        Env.loadJasyptEnvs();
    }

    @Test
    public void testGetUserAuthorityString() {
        String test = userService.getUserAuthorityString("test");
        assert StringUtils.hasText(test);
        System.out.println(test);
    }

    @Test
    public void testSave() {
        SysUser sysUser = new SysUser();
        for (int i = 0; i < 100; i++) {
//            sysUser.setUserId(-1L);
            sysUser.setUserId(null);
            sysUser.setUsername("test_account_" + i);
            sysUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            sysUser.setPhoneNumber("12345678910");
            sysUser.setEmail("test_account_" + i + "@test.com");
            sysUser.setRegisterTime(LocalDateTime.now());
            sysUser.setNickname("测试用户" + i);
            sysUser.setCreatorUsername("root");
            sysUser.setGender(i % 2);
            sysUser.setStatus(0);
            userService.saveUserInfo(sysUser);
        }
    }
}

package cc.seckill.service;

import cc.seckill.AuthMainApp;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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
}

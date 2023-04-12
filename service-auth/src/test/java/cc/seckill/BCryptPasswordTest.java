package cc.seckill;

import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * description: BCryptPasswordTest <br>
 * date: 2022/12/15 21:18 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = AuthMainApp.class)
public class BCryptPasswordTest {
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeAll
    public static void init() {
        Env.loadJasyptEnvs();
    }

    @Test
    public void testBcrypt() {
        String encode = passwordEncoder.encode("Test1!");
        assert passwordEncoder.matches("Test1!", encode);
        System.out.println(encode);
    }
}

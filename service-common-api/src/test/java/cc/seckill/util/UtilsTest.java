package cc.seckill.util;

import cc.seckill.CommonMainApp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.util
 * @Description : UtilsTest
 * @Create on : 2023/5/25 17:27
 */
@SpringBootTest(classes = CommonMainApp.class)
public class UtilsTest {
    @Resource
    private UuidUtil uuidUtil;

    @BeforeAll
    public static void init() {
        Env.loadEnvs();
    }

    @Test
    public void testUUIDByRedis() {
        String s = uuidUtil.getUUIDByRedis();
        System.out.println(s);
    }
}

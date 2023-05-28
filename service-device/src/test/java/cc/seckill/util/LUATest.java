package cc.seckill.util;

import cc.seckill.DeviceMainApp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.util
 * @Description : LUATest
 * @Create on : 2023/5/26 15:07
 */
@SpringBootTest(classes = DeviceMainApp.class)
public class LUATest {
    @Resource
    private DefaultRedisScript<List<Object>> uuidRedisScript;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @BeforeAll
    public static void init() {
        Env.loadEnvs();
    }


    @Test
    public void testLua() {
        List<String> keys = Arrays.asList("UUID_GLOBAL_COUNTER", "1");
        List<Object> execute = stringRedisTemplate.execute(uuidRedisScript, keys, "218340105584895", "1");
        assert execute != null;
        System.out.println(execute);
    }
}

package cc.seckill.test;

import cc.seckill.GatewayMainApp;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * description: RedisTest <br>
 * date: 2022/12/8 16:51 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {GatewayMainApp.class})
public class RedisTest {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeAll
    public static void init() {
        Env.loadJasyptEnvs();
    }

    @Test
    public void testRedisTemplate() {
        redisTemplate.opsForValue().set("test", "test");
    }
}

package cc.seckill;

import cc.seckill.util.Env;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * description: RedisTest <br>
 * date: 2022/12/9 10:20 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Slf4j
@SpringBootTest(classes = {AuthMainApp.class})
public class RedisTest {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @BeforeAll
    public static void init() {
        Env.loadJasyptEnvs();
    }

    @Test
    public void testRedisTemplate() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String key = sc.next();
            if ("end".equalsIgnoreCase(key)) {
                break;
            }
            String value = sc.next();
            redisTemplate.opsForValue().set(key, value, 10, TimeUnit.SECONDS);
            log.info("set {}:{} success!", key, value);

        }
        sc.close();
    }

}


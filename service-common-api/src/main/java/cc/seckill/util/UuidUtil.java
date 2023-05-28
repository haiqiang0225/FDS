package cc.seckill.util;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.util
 * @Description : UUID
 * @Create on : 2023/5/25 16:17
 */

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class IdUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static String genUUIDByRedis() {
        return null;
    }

    public static String getUUIDLocally() {
        return cn.hutool.core.util.IdUtil.fastUUID();
    }

    public static String snowFlake
}

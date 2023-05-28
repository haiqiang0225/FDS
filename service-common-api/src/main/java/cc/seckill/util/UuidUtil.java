package cc.seckill.util;


import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.util
 * @Description : UUID
 * @Create on : 2023/5/25 16:17
 */
@Component
@Slf4j
public class UuidUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private DefaultRedisScript<List<String>> uuidRedisScript;

    @Resource
    private UUIDSegmentOverflowStrategy uuidSegmentOverflowStrategy;

    /**
     * Redis UUID key
     */
    public static final String REDIS_UUID_KEY_PREFIX = "UUID_GLOBAL_COUNTER";

    /**
     * UUID 原始字符长度
     */
    public static final int UUID_RAW_LENGTH = 32;

    /**
     * UUID 最终字符串长度
     */
    public static final int UUID_LENGTH = UUID_RAW_LENGTH + 4;

    /**
     * 最多由几个counter构成uuid
     */
    public static final int MAX_COUNTERS = 4;

    /**
     * UUID 每个段的最大值
     */
    public static final long SEGMENT_MAX_VALUE = 218340105584895L;

    /**
     * UUID 每个段的长度
     */
    public static final int DEFAULT_PRE_SEG_LEN = UUID_RAW_LENGTH / MAX_COUNTERS;

    /**
     * 使用Redis生成全局唯一UUID
     * *
     *
     * @return UUID
     */
    public String getUUIDByRedis() {
        String incrPart = String.valueOf(ThreadLocalRandom.current().nextInt(1, MAX_COUNTERS + 1));
        List<String> keys = Arrays.asList(REDIS_UUID_KEY_PREFIX, incrPart);
        String increment = "1";

        List<String> executeResultList = stringRedisTemplate.execute(uuidRedisScript,
                keys,
                String.valueOf(SEGMENT_MAX_VALUE),
                increment);

        assert executeResultList != null;

        String result = executeResultList.get(0);
        if (!"ok".equalsIgnoreCase(result)) {
            if ("overflow".equalsIgnoreCase(result)) {
                uuidSegmentOverflowStrategy.apply(Integer.parseInt(incrPart));
            } else {
                throw new RuntimeException("Redis 自增时出错了, 返回值为：" + result);
            }
        }

        StringBuilder builder = new StringBuilder();
        // 处理每个分段的值
        for (int i = 1; i <= MAX_COUNTERS; i++) {
            long segLongVal = Long.parseLong(executeResultList.get(i));
            String segBase62 = NumberSystemConverter.decimalToBase62(segLongVal);
            int appendLen = DEFAULT_PRE_SEG_LEN - segBase62.length();
            segBase62 = "0".repeat(Math.max(0, appendLen)) + segBase62;
//            log.info("current segment : {}, {}", i, segBase62);
            builder.append(segBase62);
        }
        String base62 = builder.toString();
        return formatNumberToUUID(base62);
    }

    public static String getUUIDLocally() {
        return IdUtil.fastUUID();
    }

    public static String getSnowFlakeUUID() {
        return NumberSystemConverter.decimalToBase62(IdUtil.getSnowflakeNextIdStr());
    }

    /**
     * 将数字转换为符合的UUID格式，形如8-4-4-4-12的32个字符
     *
     * @param rowUUID UUID数字
     * @return 格式化后的形式
     */
    public static String formatNumberToUUID(String rowUUID) {
        int uuidLength = 32;
        int appendLength = uuidLength - rowUUID.length();
        String raw = "0".repeat(Math.max(0, appendLength)) + rowUUID;
        return String.format("%s-%s-%s-%s-%s",
                raw.substring(0, 8),
                raw.substring(8, 12),
                raw.substring(12, 16),
                raw.substring(16, 20),
                raw.substring(20));
    }

}

package cc.seckill.util.impl;

import cc.seckill.util.UUIDSegmentOverflowStrategy;
import org.springframework.stereotype.Component;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.util.impl
 * @Description : DefaultUUIDSegmentOverflowStrategy
 * @Create on : 2023/5/28 10:17
 */
@Component
public class DefaultUUIDSegmentOverflowStrategy implements UUIDSegmentOverflowStrategy {
    @Override
    public void apply(int segId) {
        throw new RuntimeException("UUID 段值溢出了，溢出的段id为： " + segId);
    }
}

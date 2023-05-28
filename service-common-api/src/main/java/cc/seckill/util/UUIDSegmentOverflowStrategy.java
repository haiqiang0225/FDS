package cc.seckill.util;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.util
 * @Description : UUIDSegmentFullStrategy
 * @Create on : 2023/5/26 14:52
 */
public interface UUIDSegmentOverflowStrategy {

    void apply(int segId);

}

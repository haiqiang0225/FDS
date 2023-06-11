package cc.seckill.mapper;

import cc.seckill.entities.KVEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.mapper
 * @Description : KVEntiryMapper
 * @Create on : 2023/6/11 15:18
 */
public interface KVEntityMapper extends BaseMapper<KVEntity<?, ?>> {

    List<KVEntity<Date, Integer>> queryRecentlyFaults(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                      @Param("start") int start, @Param("count") int count);
}

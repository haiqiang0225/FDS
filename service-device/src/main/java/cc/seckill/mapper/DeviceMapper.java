package cc.seckill.mapper;

import cc.seckill.entities.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.mapper
 * @Description : DeviceMapper
 * @Create on : 2023/5/25 10:37
 */
public interface DeviceMapper extends BaseMapper<Device> {
    List<Device> queryDeviceListByIDPaging(@Param("start") int start,
                                           @Param("count") int count,
                                           @Param("keyword") String keyword);

    List<Device> queryDeviceListByParentId(@Param("parentId") String parentId);
}

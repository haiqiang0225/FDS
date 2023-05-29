package cc.seckill.service.impl;

import cc.seckill.entity.Device;
import cc.seckill.mapper.DeviceMapper;
import cc.seckill.service.IDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jhq
 * @since 2023-05-25
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

}

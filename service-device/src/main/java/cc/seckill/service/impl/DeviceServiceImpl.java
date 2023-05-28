package cc.seckill.service.impl;

import cc.seckill.entities.Device;
import cc.seckill.mapper.DeviceMapper;
import cc.seckill.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service.impl
 * @Description : DeviceServiceImpl
 * @Create on : 2023/5/25 16:04
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public List<Device> queryDeviceListByIDPaging(int start, int count, String keyword) {
        return deviceMapper.queryDeviceListByIDPaging(start, count, keyword);
    }

    @Override
    public int addDevice(Device device) {
        return deviceMapper.insert(device);
    }

    @Override
    public int deleteDevice(Device device) {
        return deviceMapper.deleteById(device);
    }

    @Override
    public int updateDeviceInfo(Device device) {
        return deviceMapper.updateById(device);
    }
}

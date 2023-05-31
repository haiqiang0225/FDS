package cc.seckill.service;

import cc.seckill.entities.Device;

import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service
 * @Description : DeviceService
 * @Create on : 2023/5/25 16:03
 */
public interface DeviceService {
    List<Device> queryDeviceListByIDPaging(int start, int count, String keyword);

    int addDevice(Device device);

    int deleteDevice(Device device);

    int updateDeviceInfo(Device device);

    long count();
}

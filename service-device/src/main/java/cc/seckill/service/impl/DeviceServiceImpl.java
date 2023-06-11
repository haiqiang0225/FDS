package cc.seckill.service.impl;

import cc.seckill.domain.Result;
import cc.seckill.entities.Device;
import cc.seckill.entities.KVEntity;
import cc.seckill.mapper.DeviceMapper;
import cc.seckill.service.DeviceService;
import cc.seckill.util.UuidUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private UuidUtil uuidUtil;

    @Override
    public List<Device> queryDeviceListByIDPaging(int start, int count, String keyword) {
        List<Device> devices = deviceMapper.queryDeviceListByIDPaging(start, count, keyword);
        for (Device device : devices) {
            List<Device> childList = deviceMapper.queryDeviceListByParentId(device.getDeviceId());
            device.setChildrenList(childList);
        }
        return devices;
    }

    @Override
    public int addDevice(Device device) {
        log.info("添加设备 {}", device);
        device.setDeviceId(uuidUtil.getUUIDByRedis());
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

    @Override
    public long count() {
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_device_id", "").or().eq("parent_device_id", null);
        return deviceMapper.selectCount(queryWrapper);
    }

    @Override
    public Result getCountByRunningState() {
        List<KVEntity<String, Integer>> kvEntities = deviceMapper.selectCountGroupRunningState();
        Result result = new Result();
        result.put("runningStates", kvEntities);
        return result;
    }
}

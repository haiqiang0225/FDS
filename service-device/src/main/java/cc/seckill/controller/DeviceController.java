package cc.seckill.controller;

import cc.seckill.domain.Result;
import cc.seckill.entities.Device;
import cc.seckill.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.controller
 * @Description : DeviceController
 * @Create on : 2023/5/25 15:07
 */
@RestController
@RequestMapping("/device")
@Slf4j
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @RequestMapping("/list")
    public Result queryDeviceList(int start, int count, String keyword) {
        Result result = new Result();
        List<Device> devices = deviceService.queryDeviceListByIDPaging(start, count, keyword);
        long cnt = deviceService.count();
        result.put("deviceList", devices);
        result.put("count", cnt);
        return result;
    }

    @PostMapping("/add")
    public Result addDevice(@RequestBody Device device) {
        int i = deviceService.addDevice(device);
        return Result.ok().put("msg", "添加设备成功！");
    }

    @RequestMapping("/delete")
    public Result deleteDevice(Device device) {
        return Result.ok();
    }

    @PostMapping("/update")
    public Result updateDevice(Device device) {
        return Result.ok();
    }

}

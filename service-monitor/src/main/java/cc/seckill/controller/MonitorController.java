package cc.seckill.controller;

import cc.seckill.domain.Result;
import cc.seckill.service.MonitorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.controller
 * @Description : MonitorController
 * @Create on : 2023/6/2 17:14
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Resource
    private MonitorService monitorService;

    @RequestMapping("/cpuInfo")
    public Result getCpuInfo() {
        return monitorService.getCpuInfo();
    }

    @RequestMapping("/sysLoad")
    public Result getSysLoad() {
        return monitorService.getSysLoad();
    }

    @RequestMapping("/getFaultCountByDeviceType")
    public Result getFaultCountByDeviceType() {
        return monitorService.getFaultCountByDeviceType();
    }

    @RequestMapping("/getFaultCountByFaultType")
    public Result getFaultCountByFaultType() {
        return monitorService.getFaultCountByFaultType();
    }

    @RequestMapping("/getFaultCountByFaultDegree")
    public Result getFaultCountByFaultDegree() {
        return monitorService.getFaultCountByFaultDegree();
    }



}

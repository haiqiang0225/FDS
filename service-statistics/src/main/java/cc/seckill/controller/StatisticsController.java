package cc.seckill.controller;

import cc.seckill.domain.Result;
import cc.seckill.service.DeviceFeignService;
import cc.seckill.service.StatisticsService;
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
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService monitorService;

    @Resource
    private DeviceFeignService deviceFeignService;

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

    @RequestMapping("/runningStatesCount")
    public Result runningStatesCount() {
        return deviceFeignService.runningStatesCount();
    }

    @RequestMapping("/recentFaults")
    public Result recentFaults() {
        return monitorService.recentFaults();
    }


}

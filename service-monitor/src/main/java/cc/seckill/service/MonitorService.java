package cc.seckill.service;

import cc.seckill.domain.Result;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service
 * @Description : MonitorService
 * @Create on : 2023/6/2 17:15
 */
public interface MonitorService {
    Result getSysLoad();


    Result getCpuInfo();

    Result getMemoryUsage();


    Result getFaultCountByDeviceType();

    Result getFaultCountByFaultType();

    Result getFaultCountByFaultDegree();


}

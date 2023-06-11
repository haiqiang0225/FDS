package cc.seckill.service;

import cc.seckill.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service
 * @Description : DeviceService
 * @Create on : 2023/6/10 23:05
 */
@Component
@FeignClient(value = "service-device")
public interface DeviceFeignService {
    @RequestMapping("/device/runningStatesCount")
    Result runningStatesCount();
}

package cc.seckill.service;

import cc.seckill.MonitorMainApp;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service
 * @Description : MonitorServiceTest
 * @Create on : 2023/6/7 16:49
 */
@SpringBootTest(classes = MonitorMainApp.class)
public class MonitorServiceTest {

    @Resource
    private MonitorService monitorService;

    @BeforeAll
    public static void init() {
        Env.loadEnvs();
    }

    @Test
    public void testGetCpuLoad() throws InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            String cpuLoad = monitorService.getCpuLoad();
//            System.out.println(cpuLoad);
//            assert StringUtils.hasText(cpuLoad);
//            TimeUnit.SECONDS.sleep(1);
//        }
    }
}

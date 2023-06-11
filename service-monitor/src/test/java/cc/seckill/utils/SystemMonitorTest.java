package cc.seckill.utils;


import cc.seckill.MonitorMainApp;
import cc.seckill.util.Env;
import cc.seckill.util.SystemUtil;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.utils
 * @Description : SystemMonitorTest
 * @Create on : 2023/6/6 21:30
 */
@SpringBootTest(classes = MonitorMainApp.class)
public class SystemMonitorTest {

    @Resource
    private SystemUtil systemUtil;

    @BeforeAll
    public static void init() {
        Env.loadEnvs();
    }


    @Test
    public void testCpuLoad() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println(systemUtil.getCpuInfo().get("sysLoad"));
            TimeUnit.SECONDS.sleep(1);
        }
    }


}

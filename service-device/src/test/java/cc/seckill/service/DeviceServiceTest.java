package cc.seckill.service;

import cc.seckill.DeviceMainApp;
import cc.seckill.entities.Device;
import cc.seckill.util.Env;
import cc.seckill.util.UuidUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service
 * @Description : DeviceServiceTest
 * @Create on : 2023/5/28 15:04
 */
@SpringBootTest(classes = DeviceMainApp.class)
public class DeviceServiceTest {

    @Resource
    private DeviceService deviceService;

    private static Device device;

    @BeforeAll
    public static void init() {
        Env.loadEnvs();
        device = new Device();
        device.setDeviceId("a8ad5b49-a1ce-b7b6-c3b6-7e4f5b63234c");
        device.setDeviceName("Test");
        device.setDeviceStatus((byte) 0);
        device.setIpv4("127.0.0.1");
        device.setIpv6("::1");
        device.setDeviceType("NoneType");
        device.setLocation("Test");
        device.setParentDeviceId(null);
    }

    @Test
    public void testAddAndDeleteDevice() {
    }

    @Test
    public void tesUpdateDeviceInfo() {
        int i = deviceService.updateDeviceInfo(device);
        assert i >= 0;
    }

}

package cc.seckill.mapper;

import cc.seckill.DeviceMainApp;
import cc.seckill.entities.Device;
import cc.seckill.entities.KVEntity;
import cc.seckill.util.Env;
import cc.seckill.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = DeviceMainApp.class)
@Slf4j
public class DeviceMapperTest {
    @Resource
    private DeviceMapper deviceMapper;

    @BeforeAll
    public static void init() {
        Env.loadEnvs();
    }

    @Test
    public void testDeviceMapper() {
        Long aLong = deviceMapper.selectCount(null);
        log.info(">>>>>>>>> count = {}", aLong);
        assert aLong > 0;
    }

    @Test
    public void testQueryDeviceListPaging() {
        List<Device> devices = deviceMapper.queryDeviceListByIDPaging(0, 10, "A907");
        assert !devices.isEmpty();
    }

    @Test
    public void testQueryDeviceListByParentId() {
        List<Device> devices = deviceMapper.queryDeviceListByParentId("1KBRIKR3-001U-4VRI-002N-BTTF001ISLZJ");
        System.out.println(devices);
        assert !devices.isEmpty();
    }

    @Test
    public void testSelectCountGroupRunningState() {
        List<KVEntity<String, Integer>> kvEntities = deviceMapper.selectCountGroupRunningState();
        System.out.println(kvEntities);
    }

}
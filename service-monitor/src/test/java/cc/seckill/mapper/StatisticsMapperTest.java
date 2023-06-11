package cc.seckill.mapper;

import cc.seckill.MonitorMainApp;
import cc.seckill.entities.StatisticsFaultByEquipmentType;
import cc.seckill.entities.StatisticsFaultByType;
import cc.seckill.entities.StatisticsFaultDegree;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.mapper
 * @Description : StatisticsMapperTest
 * @Create on : 2023/6/10 19:57
 */
@SpringBootTest(classes = MonitorMainApp.class)
public class StatisticsMapperTest {
    @Resource
    private StatisticsFaultByEquipmentTypeMapper statisticsFaultByEquipmentTypeMapper;

    @Resource
    private StatisticsFaultByTypeMapper statisticsFaultByTypeMapper;

    @Resource
    private StatisticsFaultDegreeMapper statisticsFaultDegreeMapper;

    @BeforeAll
    public static void init() {
        Env.loadEnvs();
    }

    @Test
    public void test() {
        assert statisticsFaultByEquipmentTypeMapper != null;
        assert statisticsFaultByTypeMapper != null;
        assert statisticsFaultDegreeMapper != null;
        List<StatisticsFaultByEquipmentType> statisticsFaultByEquipmentTypes =
                statisticsFaultByEquipmentTypeMapper.selectList(null);
        System.out.println(statisticsFaultByEquipmentTypes);
        List<StatisticsFaultByType> statisticsFaultByTypes = statisticsFaultByTypeMapper.selectList(null);
        System.out.println(statisticsFaultByTypes);
        List<StatisticsFaultDegree> statisticsFaultDegrees = statisticsFaultDegreeMapper.selectList(null);
        System.out.println(statisticsFaultDegrees);
    }
}

package cc.seckill.mapper;

import cc.seckill.StatisticsMainApp;
import cc.seckill.entities.KVEntity;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.mapper
 * @Description : KVMapperTest
 * @Create on : 2023/6/11 15:23
 */
@SpringBootTest(classes = StatisticsMainApp.class)
public class KVMapperTest {


    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Resource
    private KVEntityMapper kvEntityMapper;

    @BeforeAll
    public static void init() {
        Env.loadEnvs();
    }

    @Test
    public void testQueryRecentlyFaults() {
        List<KVEntity<Date, Integer>> kvEntities = kvEntityMapper.queryRecentlyFaults(null, null, 0, 7);
        List<KVEntity<String, Integer>> kvEntityList = kvEntities.stream().map(item -> {
            Date key = item.getKey();
            Integer value = item.getValue();
            KVEntity<String, Integer> kvEntity = new KVEntity<>();
            kvEntity.setKey(DATE_TIME_FORMATTER.format(LocalDateTime.ofInstant(key.toInstant(),
                    ZoneId.systemDefault())));
            kvEntity.setValue(value);
            return kvEntity;
        }).toList();

        System.out.println(kvEntityList);

    }

}

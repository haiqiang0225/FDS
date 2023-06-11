package cc.seckill.service.impl;

import cc.seckill.domain.Result;
import cc.seckill.entities.KVEntity;
import cc.seckill.entities.StatisticsFaultByEquipmentType;
import cc.seckill.entities.StatisticsFaultByType;
import cc.seckill.entities.StatisticsFaultDegree;
import cc.seckill.mapper.KVEntityMapper;
import cc.seckill.mapper.StatisticsFaultByEquipmentTypeMapper;
import cc.seckill.mapper.StatisticsFaultByTypeMapper;
import cc.seckill.mapper.StatisticsFaultDegreeMapper;
import cc.seckill.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service.impl
 * @Description : MonitorServiceImpl
 * @Create on : 2023/6/7 16:48
 */
@Service
public class MonitorServiceImpl implements StatisticsService {


    @Resource
    private StatisticsFaultByEquipmentTypeMapper statisticsFaultByEquipmentTypeMapper;

    @Resource
    private StatisticsFaultByTypeMapper statisticsFaultByTypeMapper;

    @Resource
    private StatisticsFaultDegreeMapper statisticsFaultDegreeMapper;

    @Resource
    private KVEntityMapper kvEntityMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public Result getFaultCountByDeviceType() {
        Result result = new Result();
        List<StatisticsFaultByEquipmentType> statisticsFaultByEquipmentTypeList =
                statisticsFaultByEquipmentTypeMapper.selectList(null);
        result.put("faultCountsByDeviceType", statisticsFaultByEquipmentTypeList);
        return result;
    }

    @Override
    public Result getFaultCountByFaultType() {
        Result result = new Result();
        List<StatisticsFaultByType> statisticsFaultByTypeList = statisticsFaultByTypeMapper.selectList(null);
        List<String> types = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (StatisticsFaultByType statisticsFaultByType : statisticsFaultByTypeList) {
            types.add(statisticsFaultByType.getName());
            values.add(statisticsFaultByType.getValue());
        }
        result.put("types", types);
        result.put("values", values);
        return result;
    }

    @Override
    public Result getFaultCountByFaultDegree() {
        Result result = new Result();
        List<StatisticsFaultDegree> statisticsFaultDegreeList = statisticsFaultDegreeMapper.selectList(null);
        List<String> labels = new ArrayList<>();
        List<StatisticsFaultDegree> values = new ArrayList<>();
        for (StatisticsFaultDegree statisticsFaultDegree : statisticsFaultDegreeList) {
            labels.add(statisticsFaultDegree.getName());
            values.add(statisticsFaultDegree);
        }
        result.put("labels", labels);
        result.put("values", values);
        return result;
    }

    @Override
    public Result recentFaults() {
        List<KVEntity<Date, Integer>> kvEntities = kvEntityMapper.queryRecentlyFaults(null, null, 0, 7);

        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (KVEntity<Date, Integer> item : kvEntities) {
            Date key = item.getKey();
            Integer value = item.getValue();
            keys.add(DATE_TIME_FORMATTER.format(LocalDateTime.ofInstant(key.toInstant(), ZoneId.systemDefault())));
            values.add(value);
        }

        Result result = new Result();
        result.put("keys", keys);
        result.put("values", values);
        return result;
    }
}

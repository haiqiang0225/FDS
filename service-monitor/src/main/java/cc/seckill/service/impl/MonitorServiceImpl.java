package cc.seckill.service.impl;

import cc.seckill.domain.Result;
import cc.seckill.entities.StatisticsFaultByEquipmentType;
import cc.seckill.entities.StatisticsFaultByType;
import cc.seckill.entities.StatisticsFaultDegree;
import cc.seckill.mapper.StatisticsFaultByEquipmentTypeMapper;
import cc.seckill.mapper.StatisticsFaultByTypeMapper;
import cc.seckill.mapper.StatisticsFaultDegreeMapper;
import cc.seckill.service.MonitorService;
import cc.seckill.util.SystemUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service.impl
 * @Description : MonitorServiceImpl
 * @Create on : 2023/6/7 16:48
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Resource
    private SystemUtil systemUtil;

    @Resource
    private StatisticsFaultByEquipmentTypeMapper statisticsFaultByEquipmentTypeMapper;

    @Resource
    private StatisticsFaultByTypeMapper statisticsFaultByTypeMapper;

    @Resource
    private StatisticsFaultDegreeMapper statisticsFaultDegreeMapper;


    @Override
    public Result getSysLoad() {
        String key = "sysLoad";
        String sysLoad = systemUtil.getCpuInfo().getString(key);
        return new Result().put(key, sysLoad);
    }

    @Override
    public Result getCpuInfo() {
        return new Result(systemUtil.getCpuInfo());
    }

    @Override
    public Result getMemoryUsage() {
        return null;
    }

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
}

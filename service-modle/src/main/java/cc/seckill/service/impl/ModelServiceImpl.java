package cc.seckill.service.impl;

import cc.seckill.domain.Result;
import cc.seckill.service.ModelService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service.impl
 * @Description : ModelServiceImpl
 * @Create on : 2023/6/2 16:41
 */
@Service
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Resource
    private RestTemplate restTemplate;


    @Override
    public List<String> getModelNames() {
        return null;
    }

    @Override
    public Result predict(String modelId, JSONObject inputs) {
        return null;
    }
}

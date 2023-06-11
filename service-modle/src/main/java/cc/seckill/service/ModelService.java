package cc.seckill.service;

import cc.seckill.domain.Result;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service
 * @Description : ModelService
 * @Create on : 2023/6/2 16:41
 */
public interface ModelService {

    List<String> getModelNames();

    Result predict(String modelId, JSONObject inputs);


}

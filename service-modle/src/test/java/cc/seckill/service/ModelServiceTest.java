package cc.seckill.service;

import cc.seckill.ModelMainApp;
import cc.seckill.util.Env;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.service
 * @Description : ModelServiceTest
 * @Create on : 2023/6/2 16:47
 */
@SpringBootTest(classes = ModelMainApp.class)
public class ModelServiceTest {

    @Resource
    private RestTemplate restTemplate;

    @BeforeAll
    public static void init() {
        Env.loadEnvs();
    }

    @Test
    public void test() {
        JSONObject forObject = restTemplate.getForObject("http://tf-serving:8501/v1/models/mnist/labels/stable" +
                "/metadata", JSONObject.class);
        System.out.println(forObject.getObject("model_spec", JSONObject.class).get("name"));
    }
}

package cc.seckill.utils;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.utils
 * @Description : ParseJSONFileTest
 * @Create on : 2023/6/1 09:06
 */
public class ParseTFConfigFileTest {


    static class ModelConfigNode {
        private String name;

        private String basePath;

        private String modelPlatform;

        /**
         * String[] -> 存储versions的具体值
         */
        private Map<String, Map<String, String[]>> modelVersionPolicy;

        /**
         * String[0] -> key
         * String[1] -> value
         */
        private List<String[]> versionLabels;

    }

    interface ModelConfigFileParser {

        List<String> getModelNames();

        List<ModelConfigNode> getConfigs();

        boolean addModelConfigNode(ModelConfigNode modelConfigNode);

        default void refresh() {

        }

    }


    public static void main(String[] args) throws FileNotFoundException {
//        File file = ResourceUtils.getFile("classpath:config.conf");
//        System.out.println(file);
    }


}

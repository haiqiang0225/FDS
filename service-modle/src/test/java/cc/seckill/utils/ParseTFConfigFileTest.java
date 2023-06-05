package cc.seckill.utils;

import lombok.Data;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
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
public class ParseJSONFileTest {

    @Data
    static class Node<K, V> {
        private K key;

        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    static class ModelConfig {
        /**
         * 一级属性，不存在下级
         */
        private Map<String, String> attributes;

        private Map<String, List<Node>> childList;

    }

    interface ModelConfigFileParser {
        List<String> getModelNames();

        List<ModelConfig> getConfigs();

    }


    public static void main(String[] args) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:config.conf");
        System.out.println(file);
    }


}

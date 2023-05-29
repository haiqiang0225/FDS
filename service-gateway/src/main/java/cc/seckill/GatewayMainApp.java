package cc.seckill;

import cc.seckill.util.Env;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * description: GatewayMainApp <br>
 * date: 2022/12/7 13:25 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cc.seckill.mapper")
public class GatewayMainApp {
    public static void main(String[] args) {
        Env.loadJasyptEnvs();
        SpringApplication.run(GatewayMainApp.class, args);
    }
}

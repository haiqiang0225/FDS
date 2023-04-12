package cc.seckill;

import cc.seckill.util.Env;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * description: AuthMainApp <br>
 * date: 2022/12/8 16:16 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cc.seckill.mapper")
public class AuthMainApp {

    public static void main(String[] args) {
        Env.loadJasyptEnvs();
        ConfigurableApplicationContext run = SpringApplication.run(AuthMainApp.class, args);
    }
}

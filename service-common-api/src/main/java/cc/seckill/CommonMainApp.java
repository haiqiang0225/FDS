package cc.seckill;

import cc.seckill.util.Env;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * description: CommonMainApp <br>
 * date: 2022/12/7 10:19 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CommonMainApp {
    public static void main(String[] args) {
        Env.loadEnvs();
        SpringApplication.run(CommonMainApp.class, args);
    }
}

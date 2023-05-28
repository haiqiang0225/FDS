package cc.seckill;

import cc.seckill.util.Env;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: $NAME <br>
 * date: $DATE $TIME <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootApplication
public class ModelMainApp {
    public static void main(String[] args) {
        Env.loadJasyptEnvs();
        SpringApplication.run(ModelMainApp.class, args);
    }
}

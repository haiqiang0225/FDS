package cc.seckill;

import cc.seckill.util.Env;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MonitorMainApp {
    public static void main(String[] args) {
        Env.loadEnvs();
        SpringApplication.run(MonitorMainApp.class, args);
    }
}
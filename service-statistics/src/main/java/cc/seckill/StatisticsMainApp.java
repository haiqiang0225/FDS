package seckill.cc;

import cc.seckill.util.Env;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatisticsMainApp {
    public static void main(String[] args) {
        Env.loadEnvs();
        SpringApplication.run(StatisticsMainApp.class, args);
    }
}
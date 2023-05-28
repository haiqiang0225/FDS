package seckill.cc;

import cc.seckill.util.Env;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogMainApp {
    public static void main(String[] args) {
        Env.loadEnvs();
        SpringApplication.run(LogMainApp.class, args);
    }
}
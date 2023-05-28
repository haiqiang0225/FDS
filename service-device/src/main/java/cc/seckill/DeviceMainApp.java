package cc.seckill;


import cc.seckill.util.Env;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cc.seckill.mapper")
public class DeviceMainApp {
    public static void main(String[] args) {
        Env.loadJasyptEnvs();
        SpringApplication.run(DeviceMainApp.class, args);
    }
}
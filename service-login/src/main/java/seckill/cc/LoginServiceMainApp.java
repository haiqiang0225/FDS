package seckill.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: $NAME <br>
 * date: $DATE $TIME <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootApplication
public class LoginServiceMainApp {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", System.getenv("JASYPT_PASS"));
        SpringApplication.run(LoginServiceMainApp.class, args);
    }
}

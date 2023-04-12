package cc.seckill.mapper;

import cc.seckill.util.JwtUtil;
import io.jsonwebtoken.Claims;

/**
 * description: JWTTest <br>
 * date: 2022/12/11 14:08 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public class JWTTest {

    public static void main(String[] args) {
//        String test = JwtUtil.createJWT("test", "", 100);
//        System.out.println(test);

        String jwt = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJzdWIiOiIiLCJleHAiOjE2NzA3Mzg5MzgsImlhdCI6MTY3MDczODkzOCwianRpIjoiOWZkYzk5ZDItYjUyNS00ODFjLThhMWQtOTQyYmJjMzIyZDU5IiwidXNlcm5hbWUiOiJ0ZXN0In0.hPGe64HecC6ao7MDoKgofJGkUMDyN1lv5t6gRbxkHIA";
        Claims claims = JwtUtil.parseJWT(jwt);
        System.out.println(claims);
    }
}

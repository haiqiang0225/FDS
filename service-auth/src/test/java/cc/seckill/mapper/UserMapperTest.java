package cc.seckill.mapper;

import cc.seckill.AuthMainApp;
import cc.seckill.entities.Role;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * description: UserMapperTest <br>
 * date: 2023/4/17 10:16 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {AuthMainApp.class})
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @BeforeAll
    public static void setup() {
        Env.loadJasyptEnvs();
    }


    @Test
    public void testInsertRolesForUserByUsername() {
        String[] roleNames = {"admin", "user_admin", "role_admin", "data_analyst", "app_admin", "vendor"};
        List<Role> roleList = new ArrayList<>();
        for (String roleName : roleNames) {
            Role r = new Role();
            r.setRoleName(roleName);
            roleList.add(r);
        }
        int testAccount0 = userMapper.insertRolesForUserByUsername("test_account_0", roleList);

    }
}

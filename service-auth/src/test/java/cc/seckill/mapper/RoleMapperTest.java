package cc.seckill.mapper;

import cc.seckill.AuthMainApp;
import cc.seckill.entities.Role;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: RoleMapperTest <br>
 * date: 2022/12/29 08:49 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {AuthMainApp.class})
public class RoleMapperTest {

    @Resource
    private RoleMapper roleMapper;

    @BeforeAll
    public static void setup() {
        Env.loadJasyptEnvs();
    }


    @Test
    public void testSelectByUsername() {
        List<Role> roleList = roleMapper.selectRolesByUsername("test");
        assert !roleList.isEmpty();
        System.out.println(roleList);
    }
}

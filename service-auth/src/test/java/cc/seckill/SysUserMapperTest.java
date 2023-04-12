package cc.seckill;

import cc.seckill.entities.SysUser;
import cc.seckill.mapper.UserMapper;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: SysUserMapperTest <br>
 * date: 2022/12/10 19:49 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = AuthMainApp.class)
public class SysUserMapperTest {
    @Resource
    private UserMapper userMapper;

    @BeforeAll
    public static void init() {
        Env.loadJasyptEnvs();
    }

    @Test
    public void test() {
        List<SysUser> users = userMapper.selectList(null);
        assert !users.isEmpty();
        System.out.println(users);
    }
}

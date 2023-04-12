package cc.seckill.mapper;

import cc.seckill.AuthMainApp;
import cc.seckill.entities.Menu;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: MenuMapperTest <br>
 * date: 2022/12/29 10:01 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {AuthMainApp.class})
public class MenuMapperTest {

    @Resource
    private MenuMapper menuMapper;

    @BeforeAll
    public static void setup() {
        Env.loadJasyptEnvs();
    }


    @Test
    public void testSelectMenuListByRoleName() {
        List<Menu> menuList = menuMapper.selectMenuListByRoleName("admin");
        assert !menuList.isEmpty();
        System.out.println(menuList);
    }

}

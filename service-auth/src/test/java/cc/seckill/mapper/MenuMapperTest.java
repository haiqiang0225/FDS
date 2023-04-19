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


//    @Test
//    public void add() {
//        String[] parents = {"RoleManagement", "MenuManagement", "DeviceManagement"};
//        String[] suffixZh = {"角色", "权限", "设备"};
//        String[] permPrefix = {"system:role:", "system:menu:", "system:device:"};
//        String[] pathPrefix = {"/management/sys/role/", "/management/sys/menu/", "/management/sys/device/"};
//
//        for (int j = 0; j < parents.length; j++) {
//            String parent = parents[j];
//            String[] opts = {"Add", "Delete", "Query", "Update"};
//            String[] optsZh = {"增加", "删除", "查询", "更新"};
//
//            for (int i = 0; i < opts.length; i++) {
//                Menu m = new Menu();
//                m.setMenuName((parent + opts[i]));
//                m.setNameZh(optsZh[i] + suffixZh[j]);
//                m.setParentMenuName(parent);
//                m.setPerms(permPrefix[j] + opts[i].toLowerCase());
//                m.setPath(pathPrefix[j] + opts[i].toLowerCase());
//                m.setOrderNum(i);
//                m.setHidden(true);
//                menuMapper.insert(m);
////                System.out.println(m);
//            }
//        }
//    }

}

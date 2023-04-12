package cc.seckill.service;

import cc.seckill.AuthMainApp;
import cc.seckill.entities.Menu;
import cc.seckill.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * description: MenuServiceTest <br>
 * date: 2022/12/30 09:19 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@SpringBootTest(classes = {AuthMainApp.class})
public class MenuServiceTest {

    @Resource
    private MenuService menuService;

    @BeforeAll
    public static void setup() {
        Env.loadJasyptEnvs();
    }


    @Test
    public void testResolveMenuTreeListByUsername() {
        List<Menu> menuList = menuService.resolveMenuTreeListByUsername("test");
        assert !menuList.isEmpty();
        assert isResolvedTree(menuList);
        System.out.println(menuList);
        printTree(menuList, 0);
    }

    // 打印树形菜单,只支持打印两层的
    // Root
    // ├── index
    // ├── index2
    // ├── index3
    // └── index4
    private void printTree(List<Menu> menuList, int level) {
        if (Objects.isNull(menuList)) {
            return;
        }
        int size = menuList.size();
        for (int i = 0; i < size; i++) {
            Menu menu = menuList.get(i);
            if (level == 0) {
                System.out.println(menu.getMenuName());
                printTree(menu.getChildrenList(), level + 1);
            } else if (i != size - 1) {
                System.out.println("├── " + menu.getMenuName());
            } else {
                System.out.println("└── " + menu.getMenuName());
            }
        }

    }

    public boolean isResolvedTree(List<Menu> list) {
        for (Menu menu : list) {
            if (!Objects.isNull(menu.getParentMenuName())) {
                return false;
            }
            List<Menu> childrenList = menu.getChildrenList();
            if (!Objects.isNull(childrenList)) {
                for (Menu child : childrenList) {
                    String parentMenuName = child.getParentMenuName();
                    if (parentMenuName == null || !parentMenuName.equals(menu.getMenuName())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}

package cc.seckill.service;

import cc.seckill.entities.Menu;

import java.util.List;

/**
 * description: MenuService <br>
 * date: 2022/12/29 21:03 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public interface MenuService {


    /**
     * description: resolveMenuTreeListByUsername 根据用户名解析菜单树<br>
     * version: 1.0 <br>
     * date: 2022/12/29 21:06 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param username 用户名
     * @return java.util.List<cc.seckill.entities.Menu>
     */
    List<Menu> resolveMenuTreeListByUsername(String username);
}

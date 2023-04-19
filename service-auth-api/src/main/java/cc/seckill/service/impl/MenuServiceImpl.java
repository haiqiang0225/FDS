package cc.seckill.service.impl;

import cc.seckill.entities.Menu;
import cc.seckill.entities.Role;
import cc.seckill.mapper.MenuMapper;
import cc.seckill.mapper.RoleMapper;
import cc.seckill.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * description: MenuServiceImpl <br>
 * date: 2022/12/29 21:07 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Menu> getResolvedMenuTreeList() {
        List<Menu> menuList = menuMapper.selectList(null);
        HashMap<String, Menu> menuHashMap = new HashMap<>();
        menuList.forEach(menu -> menuHashMap.putIfAbsent(menu.getMenuName(), menu));
        return resolveMenuTreeList(menuHashMap);
    }

    @Override
    public List<Menu> resolveMenuTreeListByUsername(String username) {

        // 根据用户名获取所有角色
        List<Role> roleList = roleMapper.selectRolesByUsername(username);
        // 根据用户拥有的角色,获取所有菜单,要求不能重复
        // key是menuName, 用map可以去重并方便建立菜单
        HashMap<String, Menu> menuHashMap = new HashMap<>();
        for (Role role : roleList) {
            List<Menu> menuList = menuMapper.selectMenuListByRoleName(role.getRoleName());
            menuList.forEach(menu -> menuHashMap.putIfAbsent(menu.getMenuName(), menu));
        }

        return resolveMenuTreeList(menuHashMap);
    }


    @Override
    public List<Menu> resolveMenuTreeListByRoleName(String roleName) {
        List<Menu> menuList = menuMapper.selectMenuListByRoleName(roleName);
        HashMap<String, Menu> menuHashMap = new HashMap<>();
        menuList.forEach(menu -> menuHashMap.putIfAbsent(menu.getMenuName(), menu));

        return resolveMenuTreeList(menuHashMap);
    }

    @Override
    public List<Menu> getMenuListByRoleName(String roleName) {
        return menuMapper.selectMenuListByRoleName(roleName);
    }


    /**
     * description: resolveMenuTreeList 解析权限树 <br>
     * version: 1.0 <br>
     * date: 2023/4/17 14:49 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param menuHashMap
     * @return java.util.List<cc.seckill.entities.Menu>
     */
    public List<Menu> resolveMenuTreeList(HashMap<String, Menu> menuHashMap) {
        List<Menu> menuList = new ArrayList<>();
        // 解析菜单树🌲
        for (String menuName : menuHashMap.keySet()) {
            Menu menu = menuHashMap.get(menuName);
            // 如果是父级菜单
            if (Objects.isNull(menu.getParentMenuName())) {
                menuList.add(menu);
            } else {
                // 如果不是父级,首先找到父级及父级的子列表
                Menu parent = menuHashMap.get(menu.getParentMenuName());
                List<Menu> childrenList = parent.getChildrenList();
                // 如果父级菜单的子列表还未初始化, 则初始化
                if (Objects.isNull(childrenList)) {
                    childrenList = new ArrayList<>();
                    parent.setChildrenList(childrenList);
                }
                // 将自己添加到父级列表的子列表中
                childrenList.add(menu);
            }
        }
        // 按照OrderNum排序
        sortedMenuTreeList(menuList);

        return menuList;
    }


    private void sortedMenuTreeList(List<Menu> menuList) {
        menuList.sort(Comparator.comparingInt(Menu::getOrderNum));
        for (Menu m : menuList) {
            if (m.getChildrenList() != null) {
                sortedMenuTreeList(m.getChildrenList());
            }
        }
    }
}

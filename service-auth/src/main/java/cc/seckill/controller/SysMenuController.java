package cc.seckill.controller;

import cc.seckill.domain.Result;
import cc.seckill.entities.Menu;
import cc.seckill.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: SysMenuController <br>
 * date: 2023/4/16 15:23 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/sys/menu")
@Slf4j
public class SysMenuController {

    @Resource
    private MenuService menuService;

    @RequestMapping("/treeList")
    public Result getMenuTreeList() {
        List<Menu> menuList = menuService.getResolvedMenuTreeList();
        return Result.ok().put("menuTree", menuList);
    }



}

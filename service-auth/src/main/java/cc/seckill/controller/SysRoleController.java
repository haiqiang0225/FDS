package cc.seckill.controller;

import cc.seckill.domain.Result;
import cc.seckill.entities.Menu;
import cc.seckill.entities.Role;
import cc.seckill.service.MenuService;
import cc.seckill.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: SysRoleController <br>
 * date: 2023/4/16 09:59 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController {

    @Resource
    private RoleService roleService;


    @Resource
    private MenuService menuService;

    @RequestMapping("/list")
    public Result getRoleList(@RequestParam(value = "query", required = false) String query,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                              @RequestParam("pageSize") Integer pageSize) {

        List<Role> roleList = roleService.getRoleList(query, pageNum, pageSize);
        int totalCount;
        if (pageSize != -1) {
            totalCount = Math.toIntExact(roleService.getRoleCount(query));
        } else {
            totalCount = roleList.size();
        }
        log.info("查询角色信息, 参数: query = {} , pageNum = {}, pageSize = {}", query, pageNum, pageSize);
        Result result = Result.ok();
        result.put("roleList", roleList);
        result.put("total", totalCount);
        return result;
    }


    @RequestMapping("/delete")
    public Result deleteRoles(@RequestParam("ids") List<String> idList) {
        log.info("删除的记录的id = {}", idList);
        return Result.ok();
    }


    @RequestMapping("/menus/{id}/{roleName}")
    public Result getMenuTreeListByRoleID(@PathVariable String id, @PathVariable String roleName) {
        Result ok = Result.ok();
        if (StringUtils.hasText(roleName)) {
            roleName = roleName.trim();
            List<Menu> menuList = menuService.getMenuListByRoleName(roleName);
            ok.put("menuTree", menuList);
        }
        return ok;
    }

    @RequestMapping("/save")
    public Result saveRole(@RequestParam(value = "id", required = false) String id,
                           @RequestParam("name") String name,
                           @RequestParam("code") String code,
                           @RequestParam("remark") String remark) {
        log.info("参数: id = {} , nameZh = {}, name = {}, remark = {}", id, name, code, remark);
        Role role = new Role();
        role.setRoleNameZh(name);
        role.setRoleName(code);
        role.setRemark(remark);
        if (StringUtils.hasText(id)) { // 修改
            int integer = Integer.parseInt(id);
            if (integer != -1)
                role.setRoleId(integer);
        } else {
            role.setRoleId(null);
        }
        int save = roleService.save(role);
        if (save > 0) {
            return Result.ok();
        }

        return Result.error();
    }

}

package cc.seckill.service;

import cc.seckill.entities.Role;

import java.util.List;

/**
 * description: RoleService <br>
 * date: 2023/4/16 10:05 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public interface RoleService {

    List<Role> getRoleList(String roleName, Integer pageNum, Integer pageSize);

    Long getRoleCount(String roleName);

    int save(Role role);
}

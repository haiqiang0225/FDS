package cc.seckill.service.impl;

import cc.seckill.entities.Role;
import cc.seckill.mapper.RoleMapper;
import cc.seckill.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: RoleServiceImpl <br>
 * date: 2023/4/16 10:05 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;


    @Override
    public List<Role> getRoleList(String roleName, Integer pageNum, Integer pageSize) {
        if (!StringUtils.hasText(roleName)) {
            roleName = null;
        }
        return roleMapper.selectRolesByRoleNamePaging(roleName, (pageNum - 1) * pageSize, pageSize);
    }

    @Override
    public Long getRoleCount(String roleName) {
        Long cnt;
        if (!StringUtils.hasText(roleName)) {
            cnt = roleMapper.selectCount(null);
        } else {
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("role_name", roleName)
                    .or()
                    .like("role_name_zh", roleName);
            cnt = roleMapper.selectCount(queryWrapper);
        }
        return cnt;

    }

    @Override
    public int save(Role role) {
        if (role.getRoleId() == null) {
            return roleMapper.insert(role);
        } else {
            return roleMapper.updateById(role);
        }
    }
}

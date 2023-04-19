package cc.seckill.mapper;

import cc.seckill.entities.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: RoleMapper <br>
 * date: 2022/12/28 21:32 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRolesByUsername(@Param("username") String username);

    List<Role> selectRolesByRoleNamePaging(@Param("roleName") String roleName,
                                           @Param("start") Integer start,
                                           @Param("count") Integer count);
}

package cc.seckill.mapper;

import cc.seckill.entities.Role;
import cc.seckill.entities.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: UserMapper <br>
 * date: 2022/12/10 19:47 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public interface UserMapper extends BaseMapper<SysUser> {
    List<SysUser> selectUsersByUserNamePaging(@Param("username") String username,
                                              @Param("start") int start,
                                              @Param("count") Integer count);

    List<Role> selectRolesByUsernamePaging(@Param("username") String username,
                                           @Param("start") int start,
                                           @Param("count") Integer count);


    int deleteRolesByUsername(@Param("username") String username);

    int insertRolesForUserByUsername(@Param("username") String username, @Param("list") List<Role> roleList);

}

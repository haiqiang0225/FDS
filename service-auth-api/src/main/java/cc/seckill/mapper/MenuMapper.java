package cc.seckill.mapper;

import cc.seckill.entities.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: MenuMapper <br>
 * date: 2022/12/28 21:32 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectMenuListByRoleName(@Param("roleName") String roleName);
}

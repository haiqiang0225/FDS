package cc.seckill.service.impl;

import cc.seckill.entity.Role;
import cc.seckill.mapper.RoleMapper;
import cc.seckill.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jhq
 * @since 2023-05-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}

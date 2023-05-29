package cc.seckill.service.impl;

import cc.seckill.entity.Menu;
import cc.seckill.mapper.MenuMapper;
import cc.seckill.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}

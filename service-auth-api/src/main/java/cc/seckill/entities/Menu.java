package cc.seckill.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * description: Menu <br>
 * date: 2022/12/28 20:47 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_menu")
@ToString
@EqualsAndHashCode
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 数据库逻辑主键
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 权限名
     */
    private String menuName;

    /**
     * 权限中文名
     */
    private String nameZh;

    /**
     * 父权限id
     */
    private String parentMenuName;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单可见（0可见，1不可见）
     */
    private Boolean hidden;

    /**
     * 按钮是否可用，（0 不可用，1可用）
     */
    private Boolean enable;

    /**
     * 创建日期
     */
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;

    /**
     * 创建者ID
     */
    private Long creatorUsername;

    /**
     * 类型（D 目录 C 菜单 B 按钮）
     */
    private String type;

    private Integer orderNum;

    @TableField(exist = false)
    private List<Menu> childrenList;
}

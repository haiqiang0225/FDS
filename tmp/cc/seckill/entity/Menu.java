package cc.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jhq
 * @since 2023-05-25
 */
@TableName("tb_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 权限唯一限定名
     */
    private String menuName;

    /**
     * 菜单中文名
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
     * 菜单排序
     */
    private Integer orderNum;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单可见（0可见，1不可见）
     */
    private Byte hidden;

    /**
     * 按钮是否可用，（0 不可用，1可用）
     */
    private Byte enable;

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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getParentMenuName() {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Byte getHidden() {
        return hidden;
    }

    public void setHidden(Byte hidden) {
        this.hidden = hidden;
    }

    public Byte getEnable() {
        return enable;
    }

    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(Long creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "menuId = " + menuId +
            ", menuName = " + menuName +
            ", nameZh = " + nameZh +
            ", parentMenuName = " + parentMenuName +
            ", perms = " + perms +
            ", path = " + path +
            ", component = " + component +
            ", orderNum = " + orderNum +
            ", icon = " + icon +
            ", hidden = " + hidden +
            ", enable = " + enable +
            ", createDate = " + createDate +
            ", updateDate = " + updateDate +
            ", creatorUsername = " + creatorUsername +
            ", type = " + type +
        "}";
    }
}

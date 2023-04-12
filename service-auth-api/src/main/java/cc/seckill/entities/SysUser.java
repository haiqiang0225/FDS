package cc.seckill.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * description: SysUser <br>
 * date: 2022/12/10 19:34 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
@ToString
@EqualsAndHashCode
public class SysUser {
    /**
     * 用户唯一id/主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 密码（加密存储）
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    /**
     * 用户名，每个用户唯一，可用于登录
     */
    private String username;

    /**
     * 用户手机号，可用于登录
     */
    private String phoneNumber;

    /**
     * 用户邮箱，每个用户唯一，可用于登录
     */
    private String email;

    /**
     * 用户头像资源定位符
     */
    private String avatarUri;

    /**
     * 用户注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;

    /**
     * 用户注册ip
     */
    private String registerIp;

    /**
     * 用户昵称，每个用户唯一
     */
    private String nickname;


    /**
     * 创建该用户的用户id
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private Long creatorUsername;

    /**
     * 用户性别：0 未知， 1 男， 2女, 其余待定
     */
    private Integer gender;

    /**
     * 账户状态： 0 正常， 1 锁定， 2 注销（逻辑删除）
     */
    private Integer status;
}


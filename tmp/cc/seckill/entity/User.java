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
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一id/主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名，每个用户唯一，可用于登录
     */
    private String username;

    /**
     * 密码（加密存储）
     */
    private String password;

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
    private String creatorUsername;

    /**
     * 用户性别：0 未知， 1 男， 2女, 其余待定
     */
    private Byte gender;

    /**
     * 账户状态： 0 正常， 1 冻结， 2 注销（逻辑删除）， 3 暂时封禁，4 永久封禁
     */
    private Byte status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId = " + userId +
            ", username = " + username +
            ", password = " + password +
            ", phoneNumber = " + phoneNumber +
            ", email = " + email +
            ", avatarUri = " + avatarUri +
            ", registerTime = " + registerTime +
            ", registerIp = " + registerIp +
            ", nickname = " + nickname +
            ", creatorUsername = " + creatorUsername +
            ", gender = " + gender +
            ", status = " + status +
        "}";
    }
}

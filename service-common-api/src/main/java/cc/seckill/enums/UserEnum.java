package cc.seckill.enums;

import lombok.Getter;

/**
 * description: UserEnum 提供User(tb_user)表中状态码的枚举<br>
 * date: 2022/12/15 15:05 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Getter
public enum UserEnum {

    /**
     * Gender/性别
     */
    UN_KNOWN_GENDER(0, "未知性别"),
    WOMAN(1, "男"),
    MAN(2, "女"),

    /**
     * Status/账户状态
     */
    STATUS_NORMAL(0, "正常"),
    STATUS_DISABLE(1, "禁用"),
    STATUS_CANCELLED(2, "注销"),

    ;

    // 代码/代号/编号/数据库数字
    private final Integer code;

    // 信息
    private final String msg;

    UserEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public boolean match(Integer status) {
        return this.code.equals(status);
    }
}

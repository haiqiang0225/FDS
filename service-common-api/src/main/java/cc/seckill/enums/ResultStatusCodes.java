package cc.seckill.enums;

import lombok.Getter;

/**
 * description: ResultStatusCodes <br>
 * date: 2022/12/9 15:06 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 * <p>
 * code: x x x x <br>
 * 　　　| |_|　| <br>
 * 　　　|　|　L 状态位,0 代表成功 1代表失败 <br>
 * 　　　|　L 编号<br>
 * 　　　L 大分类
 * </p>
 */
@Getter
public enum ResultStatusCodes {
    // 成功状态码
    SUCCESS(200, "成功"),

    // 未知异常
    UNKNOWN_ERROR(5000, "未知异常,请联系系统管理员"),

    /**
     * 系统操作状态码
     */
    QUERY_SUCCESS(1000, "查询成功"),
    QUERY_FAIL(1001, "查询失败"),
    ADD_SUCCESS(1010, "新增成功"),
    ADD_FAIL(1011, "新增失败"),
    MODIFY_SUCCESS(1020, "修改成功"),
    MODIFY_FAIL(1021, "修改失败"),
    REMOVE_SUCCESS(1030, "删除成功"),
    REMOVE_FAIL(1031, "删除失败"),

    /**
     * 账户相关状态码
     */
    USERNAME_EXISTS(2001, "用户名已存在"),
    LOGIN_FAIL(2011, "登陆失败"),
    WRONG_USERNAME_OR_PASS(2021, "用户名或密码错误"),
    USER_NOT_AVAILABLE(2031, "账户不可用,请联系系统管理员"),
    LOGIN_EXPIRED(2041, "账户登录信息过期,请重新登录"),

    LOGOUT_FAIL(2051, "用户登出失败"),


    /**
     * 权限相关状态码
     */
    NO_AUTH(3001, "没有权限"),
    NO_OPT_AUTH(3011, "没有操作权限"),
    NO_SOURCE_AUTH(3021, "没有权限访问资源"),
    GRANT_AUTH_SUCCESS(3030, "授权成功"),
    GRANT_AUTH_FAIL(3031, "授权失败"),


    /**
     * 请求状态码
     */
    INVALID_PARAM(4701, "无效的请求参数"),
    ;

    // 状态码
    private final Integer code;

    // 提示信息
    private final String message;

    ResultStatusCodes(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return (this.code & 1) == 0;
    }
}

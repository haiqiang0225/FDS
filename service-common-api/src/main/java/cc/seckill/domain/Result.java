package cc.seckill.domain;


import cc.seckill.enums.ResultStatusCodes;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * description: Result <br>
 * date: 2022/12/1 09:50 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public class Result extends HashMap<String, Object> {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int UNKNOWN_ERROR_CODE = ResultStatusCodes.UNKNOWN_ERROR.getCode();

    private static final int SUCCESS_CODE = ResultStatusCodes.SUCCESS.getCode();

    public Result() {
        put("code", SUCCESS_CODE);
    }

    public static Result error() {
        return error(UNKNOWN_ERROR_CODE, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(UNKNOWN_ERROR_CODE, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static Result error(Object msg) {
        Result r = new Result();
        r.put("code", UNKNOWN_ERROR_CODE);
        r.put("msg", msg);
        return r;
    }

    public static Result ok(Object msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }

    public static Result data(Object data) {
        Result r = new Result();
        r.put("data", data);
        return r;
    }


    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }


    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }


}

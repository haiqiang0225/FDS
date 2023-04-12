package cc.seckill.exceptions;

import cc.seckill.enums.ResultStatusCodes;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description: GlobalException 需要返回给前端的全局异常<br>
 * date: 2022/12/9 15:23 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GlobalException extends RuntimeException {
    private Integer statusCode;

    public GlobalException(String msg, Integer statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public GlobalException(ResultStatusCodes codes) {
        super(codes.getMessage());
        this.statusCode = codes.getCode();
    }
}

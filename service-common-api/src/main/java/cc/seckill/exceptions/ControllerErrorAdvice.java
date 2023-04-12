package cc.seckill.exceptions;

import cc.seckill.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.AccountExpiredException;
import javax.validation.ConstraintViolationException;

/**
 * description: ControllerErrorAdvice 全局异常捕获<br>
 * date: 2022/12/10 16:13 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@ControllerAdvice
@Slf4j
public class ControllerErrorAdvice {

    @ExceptionHandler({GlobalException.class, MethodArgumentNotValidException.class,
            ConstraintViolationException.class, AccountExpiredException.class})
    @ResponseBody
    public Result handlerError(GlobalException e) {
        log.error("异常代码: {}", e.getStatusCode());
        log.error(e.getMessage());
        return Result.error(e.getStatusCode(), e.getMessage());
    }
}

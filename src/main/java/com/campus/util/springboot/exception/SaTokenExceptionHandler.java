package com.campus.util.springboot.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 黄磊
 */
@Slf4j
@Order(999)
@ConditionalOnClass(SaTokenException.class)
@RestControllerAdvice
public class SaTokenExceptionHandler extends ExceptionHandlerParent {

    public SaTokenExceptionHandler(ExceptionProperties properties) {
        super(properties);
    }

    @ExceptionHandler(value = NotLoginException.class)
    public ReturnResult handleNotLoginException(NotLoginException e) {
        log.debug("用户未登录", e);
        return getReturnResult(AliErrorCode.USER_ERROR_A0301, "您未登录，请先进行登录");
    }

    /**
     * 账号处理异常（登录、权限等等）
     */
    @ExceptionHandler(value = SaTokenException.class)
    public ReturnResult handleNotLoginException(SaTokenException e) {
        log.debug("账号处理异常", e);
        return getReturnResult(AliErrorCode.USER_ERROR_A0300, e.getMessage());
    }
}

package com.campus.util.springboot.exception;

import com.eggcampus.util.exception.result.FeignException;
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
@ConditionalOnClass(feign.FeignException.class)
@RestControllerAdvice
public class FeignExceptionHandler extends ExceptionHandlerParent {

    public FeignExceptionHandler(ExceptionProperties properties) {
        super(properties);
    }


    @ExceptionHandler(value = {FeignException.class, feign.FeignException.class})
    public ReturnResult handleFeignException(Exception e) {
        log.error("调用第二方服务异常", e);
        return getReturnResult(AliErrorCode.SERVICE_ERROR_C0001, ErrorMessage.UNKNOWN, e.getMessage());
    }
}

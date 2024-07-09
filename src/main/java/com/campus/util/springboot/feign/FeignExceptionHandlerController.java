package com.campus.util.springboot.feign;

import com.campus.util.springboot.exception.ErrorMessage;
import com.eggcampus.util.exception.result.FeignException;
import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 黄磊
 */
@Slf4j
@Order(999)
@RestControllerAdvice
public class FeignExceptionHandlerController {
    @ExceptionHandler(value = {FeignException.class, feign.FeignException.class})
    public ReturnResult handleFeignException(Exception e) {
        log.error("调用第二方服务异常", e);
        // 与第二方交互的时候可能会传输密码等隐私信息，所以为了避免隐私的泄漏，这里不返回具体的异常信息
        return ReturnResult.failure(AliErrorCode.SERVICE_ERROR_C0001, ErrorMessage.UNKNOWN_TIP,"调用第二方服务异常");
    }
}

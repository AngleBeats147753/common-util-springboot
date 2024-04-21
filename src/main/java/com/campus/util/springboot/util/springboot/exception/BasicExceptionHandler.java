package com.campus.util.springboot.util.springboot.exception;

import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.RejectedExecutionException;

import static com.eggcampus.util.result.AliErrorCode.USER_ERROR_A0400;


/**
 * @author 黄磊
 * @since 2022/6/16
 **/
@Slf4j
@RestControllerAdvice
public class BasicExceptionHandler {
    /**
     * 参数异常处理
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class,
            ConversionFailedException.class, ConstraintViolationException.class, HttpMessageNotReadableException.class})
    public ReturnResult handleMethodArgumentTypeMismatchException(HttpMessageNotReadableException e) {
        return ReturnResult.getFailureReturn(USER_ERROR_A0400, "输入的参数异常", e.getLocalizedMessage());
    }

    /**
     * JSR303 校验异常
     */
    @ExceptionHandler(value = {BindException.class})
    public ReturnResult handleMethodArgumentTypeMismatchException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        bindingResult.getAllErrors().forEach(o -> {
            FieldError error = (FieldError) o;
            if (error.isBindingFailure()) {
                stringBuilder.append(String.format("无法将%s作为%s的值", error.getRejectedValue(), error.getField())).append("\n");
            } else {
                stringBuilder.append(error.getField()).append(" : ").append(error.getDefaultMessage()).append("\n");
            }
        });
        return ReturnResult.getFailureReturn(USER_ERROR_A0400, stringBuilder.toString());
    }

    /**
     * 处理线程池队列溢出的异常
     */
    @ExceptionHandler(value = RejectedExecutionException.class)
    public ReturnResult handleRejectedExecutionException(RejectedExecutionException e) {
        log.error("线程池队列溢出", e);
        return ReturnResult.getFailureReturn(AliErrorCode.SYSTEM_ERROR_B0315, "系统繁忙中，请10分钟后再尝试", e.getMessage());
    }

//    /**
//     * 旦晓园的异常
//     */
//    @ExceptionHandler(value = EggCampusException.class)
//    public ReturnResult handleManagerException(EggCampusException e) {
//        if (e.getStringCode().startsWith("A")) {
//            log.warn(e.getMessage(), e);
//        } else {
//            log.error(e.getMessage(), e);
//        }
//        return ReturnResult.getFailureReturn(e.getCode(), e.getUserTip());
//    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(value = Throwable.class)
    public ReturnResult handleThrowable(Throwable e) {
        log.error("发生未知错误", e);
        return ReturnResult.getFailureReturn(AliErrorCode.SYSTEM_ERROR_B0001, "系统出现未知错误，请联系管理员", e.getMessage());
    }
}

package com.campus.util.springboot.exception;

import com.eggcampus.util.exception.base.AssertionFailedException;
import com.eggcampus.util.exception.result.NonLoggingManagerException;
import com.eggcampus.util.exception.result.NonLoggingServiceException;
import com.eggcampus.util.exception.result.ReturnResultException;
import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.concurrent.RejectedExecutionException;

import static com.eggcampus.util.result.AliErrorCode.USER_ERROR_A0400;


/**
 * @author 黄磊
 * @since 2022/6/16
 **/
@Slf4j
@RestControllerAdvice
public class BasicExceptionHandler {
    private static final String UNKNOWN_TIP = "系统出现未知错误，请联系管理员";
    private static final String BUSY_TIP = "系统繁忙中，请10分钟后再尝试";

    /**
     * 输入参数异常
     * <p>
     * MethodArgumentTypeMismatchException是参数类型不匹配时的异常。通过param、application/x-www-form-urlencoded类型请求体传递时都可能发生</br>
     * MissingServletRequestParameterException是使用缺少参数时的异常。通过param、application/x-www-form-urlencoded类型请求体传递时都可能发生</br>
     * HttpMessageNotReadableException是请求体数据存在问题时的异常。application/json类型请求体类型不对，或者请求体结构不正确时都可能发生</br>
     * HttpMessageConversionException是请求数据转换存在问题时的异常。没有相应的Converter的时都可能发生</br>
     * </p>
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class, HttpMessageConversionException.class})
    public ReturnResult handlePropertyAccessException(Exception e) {
        log.debug("输入的参数有问题", e);
        return ReturnResult.failure(USER_ERROR_A0400, "输入的参数有问题", e.getLocalizedMessage());
    }

    /**
     * 请求错误
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ReturnResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.debug("请求方式不支持", e);
        return ReturnResult.failure(AliErrorCode.USER_ERROR_A0400, "请求方式不支持", e.getLocalizedMessage());
    }

    /**
     * JSR303 校验异常
     */
    @ExceptionHandler(value = {BindException.class})
    public ReturnResult handleBindException(BindException e) {
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
        log.debug("输入的参数有问题", e);
        return ReturnResult.failure(USER_ERROR_A0400, stringBuilder.toString());
    }

    /**
     * 处理线程池队列溢出的异常
     */
    @ExceptionHandler(value = RejectedExecutionException.class)
    public ReturnResult handleRejectedExecutionException(RejectedExecutionException e) {
        log.error("线程池队列溢出", e);
        return ReturnResult.failure(AliErrorCode.SYSTEM_ERROR_B0315, "系统繁忙中，请10分钟后再尝试", e.getMessage());
    }

    /**
     * 处理无需记录日志的ReturnResultException异常
     */
    @ExceptionHandler(value = {NonLoggingManagerException.class, NonLoggingServiceException.class})
    public ReturnResult handleNonReturnResultException(ReturnResultException e) {
        log.debug("发生业务错误", e);
        return ReturnResult.failure(e.getCode(), e.getUserTip(), e.getErrorMessage());
    }

    /**
     * 处理需要记录日志的ReturnResultException异常
     */
    @ExceptionHandler(value = ReturnResultException.class)
    public ReturnResult handleReturnResultException(ReturnResultException e) {
        log.error("发生业务错误", e);
        return ReturnResult.failure(e.getCode(), e.getUserTip(), e.getErrorMessage());
    }

    /**
     * 处理断言异常
     */
    @ExceptionHandler(value = AssertionFailedException.class)
    public ReturnResult handleAssertionFailedException(AssertionFailedException e) {
        log.debug("断言错误", e);
        return ReturnResult.failure(AliErrorCode.USER_ERROR_A0402, e.getMessage());
    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(value = Throwable.class)
    public ReturnResult handleThrowable(Throwable e) {
        log.error("发生未知错误", e);
        return ReturnResult.failure(AliErrorCode.SYSTEM_ERROR_B0001, "系统出现未知错误，请联系管理员", e.getMessage());
    }
}

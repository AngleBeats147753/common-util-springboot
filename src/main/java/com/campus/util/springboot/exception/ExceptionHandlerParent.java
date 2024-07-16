package com.campus.util.springboot.exception;

import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;

/**
 * 异常处理类的父类，主要提供一些公共的方法
 *
 * @author 黄磊
 */
public class ExceptionHandlerParent {
    private final ExceptionProperties properties;

    public ExceptionHandlerParent(ExceptionProperties properties) {
        this.properties = properties;
    }

    protected ReturnResult getReturnResult(AliErrorCode errorCode, String userTip) {
        return this.getReturnResult(errorCode, userTip, "");
    }

    protected ReturnResult getReturnResult(AliErrorCode errorCode, String userTip, String errorMessage) {
        if (properties.isReturnErrorMessage()) {
            return ReturnResult.failure(errorCode, userTip, errorMessage);
        } else {
            return ReturnResult.failure(errorCode, userTip);
        }
    }
}

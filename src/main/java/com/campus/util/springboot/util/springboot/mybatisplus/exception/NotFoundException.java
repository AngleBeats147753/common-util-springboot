package com.campus.util.springboot.util.springboot.mybatisplus.exception;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;

/**
 * 未找到数据异常
 * <p>
 * 常用于findXxx方法，当根据条件不到数据时抛出此异常。此错误通常是由于前端传入的参数错误导致的。
 * </p>
 *
 * @author 黄磊
 */
public class NotFoundException extends MybatisPlusException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable throwable) {
        super(throwable);
    }

    public NotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
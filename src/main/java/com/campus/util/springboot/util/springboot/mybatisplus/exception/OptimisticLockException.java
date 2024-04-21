package com.campus.util.springboot.util.springboot.mybatisplus.exception;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;

/**
 * 乐观锁重试次数超过上限异常
 *
 * @author 黄磊
 */
public class OptimisticLockException extends MybatisPlusException {

    public OptimisticLockException(String message) {
        super(message);
    }

    public OptimisticLockException(Throwable throwable) {
        super(throwable);
    }

    public OptimisticLockException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

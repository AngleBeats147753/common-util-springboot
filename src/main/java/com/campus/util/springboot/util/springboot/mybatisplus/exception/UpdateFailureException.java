package com.campus.util.springboot.util.springboot.mybatisplus.exception;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;

/**
 * 数据库更新失败异常
 * <p>
 * 在更新以前通常已经校验过参数的正确性，如果更新失败，通常是由于系统原因，而非用户原因
 * </p>
 *
 * @author 黄磊
 */
public class UpdateFailureException extends MybatisPlusException {

    public UpdateFailureException(String message) {
        super(message);
    }

    public UpdateFailureException(Throwable throwable) {
        super(throwable);
    }

    public UpdateFailureException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

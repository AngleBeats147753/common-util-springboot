package com.campus.util.springboot.mybatisplus;

import com.eggcampus.util.exception.EggCampusException;

/**
 * 分页查询超出范围
 *
 * @author 黄磊
 */
public class OutOfPaginationException extends EggCampusException {
    public OutOfPaginationException() {
        super();
    }

    public OutOfPaginationException(String message) {
        super(message);
    }

    public OutOfPaginationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfPaginationException(Throwable cause) {
        super(cause);
    }
}

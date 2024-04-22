package com.campus.util.springboot.base;


import com.campus.util.springboot.application.EnableApplicationManager;
import com.campus.util.springboot.exception.EnableExceptionHandler;
import com.campus.util.springboot.log.EnableLog;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@EnableLog
@EnableExceptionHandler
@EnableApplicationManager
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface EnableBasic {
}
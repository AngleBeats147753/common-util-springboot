package com.campus.util.springboot;


import com.campus.util.springboot.application.EnableApplicationManager;
import com.campus.util.springboot.datetime.EnableDateTimeConverter;
import com.campus.util.springboot.enums.named.EnableNamedEnum;
import com.campus.util.springboot.exception.EnableExceptionHandler;
import com.campus.util.springboot.log.EnableLog;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@EnableLog
@EnableNamedEnum
@EnableDateTimeConverter
@EnableExceptionHandler
@EnableApplicationManager
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface EnableBasic {
}

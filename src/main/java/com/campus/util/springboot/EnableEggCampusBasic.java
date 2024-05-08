package com.campus.util.springboot;


import com.campus.util.springboot.application.EnableEggCampusApplication;
import com.campus.util.springboot.datetime.EnableEggCampusDateTimeConverter;
import com.campus.util.springboot.enums.named.EnableEggCampusNamedEnum;
import com.campus.util.springboot.exception.EnableEggCampusExceptionHandler;
import com.campus.util.springboot.log.EnableEggCampusLog;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@EnableEggCampusLog
@EnableEggCampusNamedEnum
@EnableEggCampusDateTimeConverter
@EnableEggCampusExceptionHandler
@EnableEggCampusApplication
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface EnableEggCampusBasic {
}

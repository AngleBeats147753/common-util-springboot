package com.campus.util.springboot.datetime;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({DateTimeConfiguration.class})
public @interface EnableEggCampusDateTimeConverter {
}

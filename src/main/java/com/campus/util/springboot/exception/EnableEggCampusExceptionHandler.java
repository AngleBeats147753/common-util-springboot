package com.campus.util.springboot.exception;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(ExceptionConfiguration.class)
public @interface EnableEggCampusExceptionHandler {
}

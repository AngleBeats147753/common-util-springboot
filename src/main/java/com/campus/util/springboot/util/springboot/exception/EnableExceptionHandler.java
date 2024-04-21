package com.campus.util.springboot.util.springboot.exception;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({BasicExceptionHandler.class})
public @interface EnableExceptionHandler {
}

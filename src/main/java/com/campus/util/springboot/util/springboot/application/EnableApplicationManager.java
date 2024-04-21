package com.campus.util.springboot.util.springboot.application;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ApplicationAutoConfiguration.class})
public @interface EnableApplicationManager {
}

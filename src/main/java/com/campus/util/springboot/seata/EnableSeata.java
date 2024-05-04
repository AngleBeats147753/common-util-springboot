package com.campus.util.springboot.seata;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(SeataAutoConfiguration.class)
public @interface EnableSeata {
}

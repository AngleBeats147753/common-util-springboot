package com.campus.util.springboot.util.springboot.log;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({LogAOP.class})
public @interface EnableLog {
}

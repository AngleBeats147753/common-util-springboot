package com.campus.util.springboot.feign;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(FeignConfiguration.class)
public @interface EnableEggCampusFeign {
}

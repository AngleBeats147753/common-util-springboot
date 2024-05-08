package com.campus.util.springboot.mybatisplus;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(MybatisPlusConfiguration.class)
public @interface EnableEggCampusMybatisPlus {
}

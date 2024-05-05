package com.campus.util.springboot.application;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用应用信息工具
 * <p>
 * 该注解会注入一个{@link EggCampusApplicationManager}实例，用于获取应用信息
 * </p>
 *
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({EggCampusApplicationAutoConfiguration.class})
public @interface EnableEggCampusApplication {
}

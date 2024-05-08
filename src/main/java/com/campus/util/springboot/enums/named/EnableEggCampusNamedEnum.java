package com.campus.util.springboot.enums.named;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启枚举名称自动转换
 *
 * @author 黄磊
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({NamedEnumConfiguration.class})
public @interface EnableEggCampusNamedEnum {
}

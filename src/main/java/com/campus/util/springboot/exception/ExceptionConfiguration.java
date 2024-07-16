package com.campus.util.springboot.exception;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 黄磊
 */
@Configuration
@EnableConfigurationProperties(ExceptionProperties.class)
@Import({MvcExceptionHandler.class,
        FeignExceptionHandler.class,
        MybatisPlusExceptionHandler.class,
        SaTokenExceptionHandler.class})
public class ExceptionConfiguration {

}

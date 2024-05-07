package com.campus.util.springboot.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 黄磊
 */
@Slf4j
@Configuration
@Import({LogAOP.class, ReReadableRequestFilter.class})
public class LogConfiguration implements WebMvcConfigurer {
    // 用于提醒要将logging.pattern.level设置为'%5p [%X{traceId}]'
    @Value("${logging.pattern.level}")
    private String logPatternLevel;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor());
    }
}

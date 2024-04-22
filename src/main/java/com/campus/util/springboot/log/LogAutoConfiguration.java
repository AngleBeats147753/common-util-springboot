package com.campus.util.springboot.log;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 黄磊
 */
@Configuration
@Import({LogAOP.class, MDCInterceptor.class, ReReadableRequestFilter.class})
public class LogAutoConfiguration {
}

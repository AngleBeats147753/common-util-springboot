package com.campus.util.springboot.util.springboot.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author 黄磊
 */
public class ApplicationAutoConfiguration {
    @Bean
    @Primary
    public ApplicationManager applicationManager(@Value("${spring.application.name}") String name,
                                                 @Value("${spring.profiles.active}") String profile) {
        if ("${spring.application.name}".equals(name)) {
            throw new IllegalArgumentException("spring.application.name未配置");
        }
        if ("${spring.profiles.active}".equals(profile)) {
            throw new IllegalArgumentException("spring.profiles.active未配置");
        }
        return new ApplicationManagerImpl(name, profile);
    }
}

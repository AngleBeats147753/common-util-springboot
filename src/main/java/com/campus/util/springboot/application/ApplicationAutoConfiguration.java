package com.campus.util.springboot.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author 黄磊
 */
@Slf4j
public class ApplicationAutoConfiguration {
    @Bean(name = "com.campus.util.springboot.applicationManager")
    @Primary
    public ApplicationManager applicationManager(@Value("${spring.application.name}") String name,
                                                 @Value("${spring.profiles.active}") String profile) {
        if ("${spring.application.name}".equals(name)) {
            log.warn("缺少spring.application.name配置，不生成com.campus.util.springboot.application.ApplicationManager实例");
            return null;
        }
        if ("${spring.profiles.active}".equals(profile)) {
            log.warn("缺少spring.profiles.active配置，不生成com.campus.util.springboot.application.ApplicationManager实例");
            return null;
        }
        return new ApplicationManagerImpl(name, profile);
    }
}

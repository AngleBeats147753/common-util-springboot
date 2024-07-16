package com.campus.util.springboot.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;

/**
 * @author 黄磊
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(EggCampusApplicationProperties.class)
public class EggCampusApplicationConfiguration implements EnvironmentAware {
    @Resource
    private EggCampusApplicationProperties eggCampusApplicationProperties;
    private Environment environment;

    @Bean
    @Primary
    public EggCampusApplicationManager eggCampusApplicationManager() {
        return new EggCampusApplicationManagerImpl(eggCampusApplicationProperties, environment);
    }

    @Bean
    public ApplicationStatusManager eggCampusApplicationStatusManager() {
        return new ApplicationStatusManagerImpl(eggCampusApplicationManager());
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}

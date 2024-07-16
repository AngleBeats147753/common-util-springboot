package com.campus.util.springboot.mybatisplus;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author 黄磊
 */
@EnableConfigurationProperties(EggCampusMybatisPlusProperties.class)
public class EggCampusMybatisPlusConfiguration {
    @Resource
    private EggCampusMybatisPlusProperties properties;

    @PostConstruct
    public void init() {
        PageUtil.setCrypto(properties.getKey());
    }
}

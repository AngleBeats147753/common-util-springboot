package com.campus.util.springboot.mybatisplus;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 黄磊
 */
@Data
@ConfigurationProperties("eggcampus.mybatis")
public class EggCampusMybatisPlusProperties {
    /**
     * 密钥
     */
    private String key = "123456";
}

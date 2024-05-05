package com.campus.util.springboot.application;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 黄磊
 */
@Data
@ConfigurationProperties("eggcampus.application")
public class EggCampusApplicationProperties {
    private String projectName;
    private String serviceName;
    private String profile;
}

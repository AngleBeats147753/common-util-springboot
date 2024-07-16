package com.campus.util.springboot.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 黄磊
 */
@Data
@ConfigurationProperties("eggcampus.exception")
public class ExceptionProperties {
    private boolean printErrorMessage = false;
}

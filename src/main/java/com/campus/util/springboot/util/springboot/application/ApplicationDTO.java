package com.campus.util.springboot.util.springboot.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 应用信息
 *
 * @author 黄磊
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用环境
     */
    private String profile;
}

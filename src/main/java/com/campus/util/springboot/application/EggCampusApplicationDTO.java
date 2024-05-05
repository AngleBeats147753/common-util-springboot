package com.campus.util.springboot.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * 应用信息
 *
 * @author 黄磊
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EggCampusApplicationDTO {
    /**
     * 项目名称
     */
    @NotEmpty(message = "projectName不能为null")
    private String projectName;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 应用环境
     */
    @NotEmpty(message = "profile不能为null")
    private String profile;

    public String getServiceName() {
        return Objects.isNull(serviceName) ? projectName : serviceName;
    }
}

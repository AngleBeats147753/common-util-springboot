package com.campus.util.springboot.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 50, message = "projectName长度不能超过50")
    @NotEmpty(message = "projectName不能为null")
    private String projectName;
    /**
     * 服务名称
     */
    @Length(max = 50, message = "serviceName长度不能超过50")
    private String serviceName;
    /**
     * 应用环境
     */
    @Length(max = 50, message = "profile长度不能超过50")
    @NotEmpty(message = "profile不能为null")
    private String profile;

    public String getServiceName() {
        return Objects.isNull(serviceName) ? projectName : serviceName;
    }
}

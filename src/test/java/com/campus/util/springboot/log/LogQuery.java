package com.campus.util.springboot.log;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 黄磊
 */
@Data
public class LogQuery {
    @NotEmpty(message = "param1不能为空")
    private String param1;
}

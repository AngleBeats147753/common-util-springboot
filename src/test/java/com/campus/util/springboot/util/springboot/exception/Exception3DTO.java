package com.campus.util.springboot.util.springboot.exception;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 黄磊
 */
@Data
public class Exception3DTO {
    @NotEmpty(message = "param1不能为空")
    private String param1;
}
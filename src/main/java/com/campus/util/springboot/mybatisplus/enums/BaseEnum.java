package com.campus.util.springboot.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author 黄磊
 */
public interface BaseEnum<T extends Serializable> extends IEnum<T> {
    @JsonValue
    String getName();
}

package com.campus.util.springboot.enums.named;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 命名枚举
 *
 * @author 黄磊
 */
public interface NamedEnum {
    /**
     * 获取枚举名
     *
     * @return 枚举名
     */
    @JsonValue
    String getName();
}

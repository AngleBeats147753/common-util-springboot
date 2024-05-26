package com.campus.util.springboot.enums.named;

import com.fasterxml.jackson.annotation.JsonValue;

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

    default <T extends NamedEnum> T convert(Class<T> enumClass) {
        return Name2EnumCache.get(enumClass, getName());
    }
}

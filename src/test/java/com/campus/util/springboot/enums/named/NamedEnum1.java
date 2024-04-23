package com.campus.util.springboot.enums.named;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

/**
 * @author 黄磊
 */
@Getter
@JsonDeserialize(using = NamedEnumDeserializer.class)
public enum NamedEnum1 implements NamedEnum {
    VALUE1("1-value1"), VALUE2("1-value2"), VALUE3("c-value1");

    private final String name;

    NamedEnum1(String name) {
        this.name = name;
    }
}


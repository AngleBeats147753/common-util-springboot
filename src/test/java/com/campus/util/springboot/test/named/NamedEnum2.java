package com.campus.util.springboot.test.named;

import com.campus.util.springboot.enums.named.NamedEnum;
import com.campus.util.springboot.enums.named.NamedEnumDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

/**
 * @author 黄磊
 */
@Getter
@JsonDeserialize(using = NamedEnumDeserializer.class)
public enum NamedEnum2 implements NamedEnum {
    VALUE1("2-value1"),
    VALUE2("2-value2"),
    VALUE3("c-value1");

    private final String name;

    NamedEnum2(String name) {
        this.name = name;
    }
}

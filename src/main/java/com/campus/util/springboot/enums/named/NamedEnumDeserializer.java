package com.campus.util.springboot.enums.named;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;

/**
 * JSON转换为NamedEnum时的反序列化器
 *
 * @author 黄磊
 */
@SuppressWarnings("unchecked")
public class NamedEnumDeserializer<T extends NamedEnum> extends JsonDeserializer<T> implements ContextualDeserializer {
    private Class<T> enumClass;

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        this.enumClass = (Class<T>) property.getType().getRawClass();
        return this;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String name = p.getValueAsString();
        return Name2EnumCache.get(enumClass, name);
    }
}

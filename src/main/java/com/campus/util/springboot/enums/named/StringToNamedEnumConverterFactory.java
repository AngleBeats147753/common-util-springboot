package com.campus.util.springboot.enums.named;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字符串转换为NamedEnum时的转换器
 *
 * @author 黄磊
 */
@Slf4j
@SuppressWarnings("unchecked")
public class StringToNamedEnumConverterFactory implements ConverterFactory<String, NamedEnum> {
    private static final Map<Class<?>, Converter<String, ? extends NamedEnum>> CONVERTER_MAP = new ConcurrentHashMap<>();

    @Override
    public <T extends NamedEnum> Converter<String, T> getConverter(Class<T> targetType) {
        if (CONVERTER_MAP.containsKey(targetType)) {
            return (Converter<String, T>) CONVERTER_MAP.get(targetType);
        }
        Converter<String, T> converter = new StringToEnumConverter<>(targetType);
        CONVERTER_MAP.put(targetType, converter);
        return converter;
    }

    static class StringToEnumConverter<T extends NamedEnum> implements Converter<String, T> {
        private final Class<T> enumType;

        StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(@NotNull String source) {
            return (T) Name2EnumCache.get(enumType, source);
        }
    }
}

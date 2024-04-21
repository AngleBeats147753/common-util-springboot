package com.campus.util.springboot.util.springboot.mybatisplus.enums;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 黄磊
 */
@Slf4j
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum<?>> {
    private static final Map<Class<?>, Converter<String, ? extends BaseEnum<?>>> CONVERTER_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseEnum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        Converter<String, T> converter = (Converter<String, T>) CONVERTER_MAP.get(targetType);
        if (converter == null) {
            synchronized (CONVERTER_MAP) {
                converter = (Converter<String, T>) CONVERTER_MAP.get(targetType);
                if (converter == null) {
                    converter = new StringToEnumConverter<>(targetType);
                    CONVERTER_MAP.put(targetType, converter);
                }
            }
        }
        return converter;
    }

    static class StringToEnumConverter<T extends BaseEnum<?>> implements Converter<String, T> {

        private final Map<String, T> enumMap = new ConcurrentHashMap<>();

        StringToEnumConverter(Class<T> enumType) {
            T[] enums = enumType.getEnumConstants();

            for (T e : enums) {
                enumMap.put(e.getName(), e);
            }
        }


        @Override
        public T convert(@NotNull String source) {
            // 获取
            T t = enumMap.get(source);
            if (t == null) {
                throw new IllegalArgumentException("该字符串找不到对应的枚举对象 字符串:" + source);
            }
            return t;
        }
    }
}

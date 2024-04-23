package com.campus.util.springboot.datetime;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenzhefan
 * @date 2024/2/7 6:00 PM
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    private final DateTimeFormatter formatter;

    public StringToLocalDateTimeConverter(String dateTimeFormat) {
        this.formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }
    @Override
    public LocalDateTime convert(String source) {
        if (ObjectUtil.isNull(source) || ObjectUtil.isEmpty(source)) {
            return null;
        }
        return LocalDateTime.parse(source, formatter);
    }
}

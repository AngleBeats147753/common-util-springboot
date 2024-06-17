package com.campus.util.springboot.datetime;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    private final DateTimeFormatter formatter;

    public StringToLocalTimeConverter(String dateTimeFormat) {
        this.formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    @Override
    public LocalTime convert(String source) {
        if (ObjectUtil.isNull(source) || ObjectUtil.isEmpty(source)) {
            return null;
        }
        return LocalTime.parse(source, formatter);
    }
}

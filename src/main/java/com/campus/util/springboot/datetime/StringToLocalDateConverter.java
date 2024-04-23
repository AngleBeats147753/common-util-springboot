package com.campus.util.springboot.datetime;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author chenzhefan
 * @date 2024/2/7 6:00 PM
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    private final DateTimeFormatter formatter;

    public StringToLocalDateConverter(String dateTimeFormat) {
        this.formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }
    @Override
    public LocalDate convert(String source) {
        if (ObjectUtil.isNull(source) || ObjectUtil.isEmpty(source)) {
            return null;
        }
        return LocalDate.parse(source, formatter);
    }
}

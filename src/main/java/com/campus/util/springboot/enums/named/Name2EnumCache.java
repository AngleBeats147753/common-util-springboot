package com.campus.util.springboot.enums.named;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根据类名、枚举名查询枚举结果缓存
 *
 * @author 黄磊
 */
public class Name2EnumCache {
    public static final Map<String, Map<String, NamedEnum>> NAME_2_ENUM_MAP = new ConcurrentHashMap<>();

    public static void add(Class<? extends NamedEnum> enumClass) {
        if (NAME_2_ENUM_MAP.containsKey(enumClass.getName())) {
            return;
        }
        NAME_2_ENUM_MAP.computeIfAbsent(enumClass.getName(), key -> {
            HashMap<String, NamedEnum> map = new HashMap<>();
            for (NamedEnum namedEnum : enumClass.getEnumConstants()) {
                map.put(namedEnum.getName(), namedEnum);
            }
            return map;
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends NamedEnum> T get(Class<T> enumClass, String name) {
        if (!NAME_2_ENUM_MAP.containsKey(enumClass.getName())) {
            add(enumClass);
        }
        Map<String, NamedEnum> map = NAME_2_ENUM_MAP.get(enumClass.getName());
        T result = (T) map.get(name);
        if (result == null) {
            throw new IllegalArgumentException("无法将字符串转换为枚举，类型<%s>，字符串<%s>".formatted(enumClass.getName(), name));
        }
        return result;
    }
}

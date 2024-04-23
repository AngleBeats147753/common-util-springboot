package com.campus.util.springboot.mybatisplus;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.campus.util.springboot.enums.named.NamedEnum;

import java.io.Serializable;

/**
 * 从客户端根据名称获取枚举，然后在数据库中使用其他类型存储
 *
 * @author 黄磊
 */
public interface BaseEnum<T extends Serializable> extends IEnum<T>, NamedEnum {
}

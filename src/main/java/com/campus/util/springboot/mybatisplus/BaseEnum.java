package com.campus.util.springboot.mybatisplus;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.campus.util.springboot.enums.named.NamedEnum;

import java.io.Serializable;

/**
 * 同时继承了 IEnum 和 NameEnum 的枚举接口。允许从客户端根据中文获取枚举，然后在数据库中存储为tinyint
 *
 * @author 黄磊
 */
public interface BaseEnum<T extends Serializable> extends IEnum<T>, NamedEnum {
}

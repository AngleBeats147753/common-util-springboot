package com.campus.util.springboot.exception;

/**
 * 常用的异常提示信息
 * <p>
 *     假如以TIP结尾，代表这个通常是给用户返回的提示信息</br>
 *     假如以Message结尾，代表这个通常是给开发者的提示信息</br>
 * </p>
 *
 * @author 黄磊
 */
public class ErrorMessage {
    public static final String UNKNOWN_TIP = "系统出现未知错误，请联系管理员";
    public static final String BUSY_TIP = "系统繁忙中，请10分钟后再尝试";
}

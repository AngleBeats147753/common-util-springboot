package com.campus.util.springboot.exception;

/**
 * 常用的异常提示信息
 * <p>
 * 假如以TIP结尾，代表这个通常是给用户返回的提示信息</br>
 * 假如以Message结尾，代表这个通常是给开发者的提示信息</br>
 * </p>
 *
 * @author 黄磊
 */
public class ErrorMessage {
    public static final String UNKNOWN = "系统出现未知错误，请联系管理员";
    public static final String BUSY = "系统繁忙中，请10分钟后再尝试";
    public static final String PARAMETER_ERROR = "输入的参数有问题";
    public static final String REQUEST_METHOD_NOT_SUPPORTED = "请求方式不支持";
}

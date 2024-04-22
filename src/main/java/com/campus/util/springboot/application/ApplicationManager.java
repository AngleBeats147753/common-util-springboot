package com.campus.util.springboot.application;

/**
 * @author 黄磊
 */
public interface ApplicationManager {
    /**
     * 获取应用信息
     * <p>
     * 实现类应该保证返回的对象不为null，且name、profile不为null或空字符串
     * </p>
     *
     * @return 应用信息
     */
    ApplicationDTO findApplication();
}

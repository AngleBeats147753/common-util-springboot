package com.campus.util.springboot.application;

/**
 * 获取应用信息的接口
 *
 * @author 黄磊
 */
public interface EggCampusApplicationManager {
    /**
     * 获取应用信息
     *
     * @return 应用信息
     */
    EggCampusApplicationDTO getApplication();
}

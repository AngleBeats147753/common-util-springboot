package com.campus.util.springboot.application;

/**
 * 应用状态工具
 *
 * @author 黄磊
 */
public interface ApplicationStatusManager {
    /**
     * 检查当前环境是否为开发环境。
     *
     * @return 如果当前环境是开发环境，则返回true；否则返回false。
     */
    boolean isDev();

    /**
     * 检查当前环境是否为测试环境。
     *
     * @return 如果当前环境是测试环境，则返回true；否则返回false。
     */
    boolean isTest();

    /**
     * 检查当前环境是否为生产环境。
     *
     * @return 如果当前环境是生产环境，则返回true；否则返回false。
     */
    boolean isPro();

    /**
     * 检查当前环境是否为开发环境或测试环境。
     *
     * @return 如果当前环境是开发环境或测试环境，则返回true；否则返回false。
     */
    boolean isDevOrTest();
}

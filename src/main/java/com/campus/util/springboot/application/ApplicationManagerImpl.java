package com.campus.util.springboot.application;

import org.springframework.util.StringUtils;

/**
 * @author 黄磊
 */
public class ApplicationManagerImpl implements ApplicationManager {
    private final ApplicationDTO applicationDTO;

    public ApplicationManagerImpl(String name, String profile) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("name不能为空");
        }
        if (!StringUtils.hasText(profile)) {
            throw new IllegalArgumentException("profile不能为空");
        }
        applicationDTO = new ApplicationDTO(name, profile);
    }

    @Override
    public ApplicationDTO findApplication() {
        return applicationDTO;
    }
}

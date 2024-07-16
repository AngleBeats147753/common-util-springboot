package com.campus.util.springboot.application;

/**
 * @author 黄磊
 */
public class ApplicationStatusManagerImpl implements ApplicationStatusManager {
    private final EggCampusApplicationDTO applicationDTO;

    public ApplicationStatusManagerImpl(EggCampusApplicationManager applicationManager) {
        this.applicationDTO = applicationManager.getApplication();
    }

    @Override
    public boolean isDev() {
        return "dev".equals(this.applicationDTO.getProfile());
    }

    @Override
    public boolean isTest() {
        return "test".equals(this.applicationDTO.getProfile());
    }

    @Override
    public boolean isPro() {
        return "pro".equals(this.applicationDTO.getProfile());
    }

    @Override
    public boolean isDevOrTest() {
        return isDev() || isTest();
    }
}

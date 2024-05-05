package com.campus.util.springboot.application;

import org.springframework.core.env.Environment;

/**
 * @author 黄磊
 */
public class EggCampusApplicationManagerImpl implements EggCampusApplicationManager {
    private final EggCampusApplicationDTO eggCampusApplicationDTO;

    public EggCampusApplicationManagerImpl(EggCampusApplicationProperties properties, Environment environment) {
        String projectName;
        String serviceName;
        String profile;

        if ((projectName = properties.getProjectName()) == null && (projectName = environment.getProperty("spring.application.name")) == null) {
            throw new RuntimeException("缺少eggcampus.application.name或spring.application.name配置");
        }
        if ((serviceName = properties.getServiceName()) == null) {
            serviceName = projectName;
        }
        if ((profile = properties.getProfile()) == null && (profile = environment.getProperty("spring.profiles.active")) == null) {
            throw new RuntimeException("缺少eggcampus.application.profile或spring.profiles.active配置");
        }

        this.eggCampusApplicationDTO = new EggCampusApplicationDTO(projectName, serviceName, profile);
    }

    @Override
    public EggCampusApplicationDTO getApplication() {
        return eggCampusApplicationDTO;
    }
}

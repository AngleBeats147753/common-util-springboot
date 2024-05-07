package com.campus.util.springboot.test.application;

import com.campus.util.springboot.application.EggCampusApplicationDTO;
import com.campus.util.springboot.application.EggCampusApplicationManager;
import com.campus.util.springboot.application.EnableEggCampusApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 黄磊
 */
@SpringBootTest(properties = {
        "eggcampus.application.projectName=dada",
        "eggcampus.application.serviceName=oms",
        "eggcampus.application.profile=dev",
})
@EnableEggCampusApplication
public class EnableEggCampusApplicationTests {
    @Autowired
    private EggCampusApplicationManager applicationManager;

    @Test
    @DisplayName("@EnableEggCampusApplication注入EggCampusApplicationManager成功")
    void test_applicationManager() {
        EggCampusApplicationDTO result = applicationManager.getApplication();
        EggCampusApplicationDTO gt = new EggCampusApplicationDTO("dada", "oms", "dev");
        Assertions.assertThat(result).isEqualTo(gt);
    }
}

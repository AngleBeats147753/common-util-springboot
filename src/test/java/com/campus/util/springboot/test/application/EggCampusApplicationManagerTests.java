package com.campus.util.springboot.test.application;

import cn.hutool.json.JSONObject;
import com.campus.util.springboot.application.EggCampusApplicationAutoConfiguration;
import com.campus.util.springboot.application.EggCampusApplicationDTO;
import com.campus.util.springboot.application.EggCampusApplicationManager;
import com.eggcampus.util.test.TestUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.env.MockEnvironment;

import java.nio.file.Path;
import java.util.HashMap;

/**
 * @author 黄磊
 */
public class EggCampusApplicationManagerTests {
    private static final Path baseDir = Path.of("application/ApplicantManager");

    private AnnotationConfigApplicationContext context;

    @ParameterizedTest(name = "{1}")
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            caseName, description,          exception
            case1   , 所有配置都存在                   , null
            case2   , spring.application.name不存在, null
            case3   , spring.profiles.active不存在, null
            case4   , eggcampus.application.projectName不存在, null
            case5   , eggcampus.application.serviceName不存在, null
            case6   , eggcampus.application.profile不存在, null
            case7   , eggcampus.application.projectName和spring.application.name不存在, java.lang.RuntimeException
            case8   , eggcampus.application.profile和spring.profiles.active不存在, java.lang.RuntimeException
            """)
    public void test_getApplication(ArgumentsAccessor args) throws Exception {
        HashMap<String, Object> map = prepare_getApplication(args);
        Object result = execute_getApplication(map.get("exception"));
        compare_getApplication(map.get("gt"), result);
        context.close();
    }

    private HashMap<String, Object> prepare_getApplication(ArgumentsAccessor args) throws Exception {
        Path caseDir = baseDir.resolve("getApplication").resolve(args.getString(0));

        // 准备输入
        JSONObject inputParam = TestUtil.getInputParam(caseDir);
        MockEnvironment mockEnvironment = new MockEnvironment();
        if (inputParam.containsKey("eggcampus.application.projectName")) {
            mockEnvironment.setProperty("eggcampus.application.projectName", inputParam.getStr("eggcampus.application.projectName"));
        }
        if (inputParam.containsKey("eggcampus.application.serviceName")) {
            mockEnvironment.setProperty("eggcampus.application.serviceName", inputParam.getStr("eggcampus.application.serviceName"));
        }
        if (inputParam.containsKey("eggcampus.application.profile")) {
            mockEnvironment.setProperty("eggcampus.application.profile", inputParam.getStr("eggcampus.application.profile"));
        }
        if (inputParam.containsKey("spring.application.name")) {
            mockEnvironment.setProperty("spring.application.name", inputParam.getStr("spring.application.name"));
        }
        if (inputParam.containsKey("spring.profiles.active")) {
            mockEnvironment.setProperty("spring.profiles.active", inputParam.getStr("spring.profiles.active"));
        }
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.setEnvironment(mockEnvironment);
        System.out.println("mockEnvironment = " + mockEnvironment);
        this.context = context;

        // 准备异常
        Class<?> exception = TestUtil.getException(args.getString(2));

        // 准备gt
        Object gt = TestUtil.getGt(caseDir, EggCampusApplicationDTO.class);

        HashMap<String, Object> result = new HashMap<>();
        result.put("exception", exception);
        result.put("gt", gt);

        return result;
    }

    private Object execute_getApplication(Object exception) {
        return TestUtil.execute(null, exception, (param -> {
            context.register(EggCampusApplicationAutoConfiguration.class);
            context.refresh();
            EggCampusApplicationManager eggcampusApplicationManager = this.context.getBean(EggCampusApplicationManager.class);
            return eggcampusApplicationManager.getApplication();
        }));
    }

    private void compare_getApplication(Object gt, Object result) {
        TestUtil.assertObjectEqual(result, gt);
    }

}

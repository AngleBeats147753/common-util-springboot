package com.campus.util.springboot.application;

import cn.hutool.json.JSONObject;
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
public class ApplicationManagerTests {
    private static final Path baseDir = Path.of("application/ApplicantManager");

    private AnnotationConfigApplicationContext context;

    @ParameterizedTest(name = "{1}")
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            caseName, description,          exceptionf
            case1   , 正常           , null
            case2   , spring.application.name未配置, org.springframework.beans.factory.BeanCreationException
            case3   , spring.profiles.active未配置, org.springframework.beans.factory.BeanCreationException
            case4   , spring.application.name为空字符串, org.springframework.beans.factory.BeanCreationException
            case5   , spring.profiles.active为空字符串, org.springframework.beans.factory.BeanCreationException
            """)
    public void test_getApplication(ArgumentsAccessor args) throws Exception {
        HashMap<String, Object> map = prepare_getApplication(args);
        Object result = execute_getApplication(map.get("exception"));
        compare_getApplication(map.get("gt"), result);
        context.close();
    }

    private HashMap<String, Object> prepare_getApplication(ArgumentsAccessor args) throws Exception {
        Path caseDir = baseDir.resolve("findApplication").resolve(args.getString(0));

        // 准备输入
        JSONObject inputParam = TestUtil.getInputParam(caseDir);
        MockEnvironment mockEnvironment = new MockEnvironment();
        if (inputParam.containsKey("name")) {
            mockEnvironment.setProperty("spring.application.name", inputParam.getStr("name"));
        }
        if (inputParam.containsKey("profile")) {
            mockEnvironment.setProperty("spring.profiles.active", inputParam.getStr("profile"));
        }
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.setEnvironment(mockEnvironment);
        this.context = context;

        // 准备异常
        Class<?> exception = TestUtil.getException(args.getString(2));

        // 准备gt
        Object gt = TestUtil.getGt(caseDir, ApplicationDTO.class);

        HashMap<String, Object> result = new HashMap<>();
        result.put("exception", exception);
        result.put("gt", gt);

        return result;
    }

    private Object execute_getApplication(Object exceptionObject) {
        return TestUtil.execute(null, exceptionObject, (param -> {
            context.register(ApplicationAutoConfiguration.class);
            context.refresh();
            ApplicationManager applicationManager = this.context.getBean(ApplicationManager.class);
            return applicationManager.findApplication();
        }));
    }

    private void compare_getApplication(Object gt, Object result) {
        TestUtil.assertObjectEqual(result, gt);
    }

}

package com.campus.util.springboot.test.mybatisplus;

import cn.hutool.json.JSONUtil;
import com.campus.util.springboot.mybatisplus.EnableMybatisPlus;
import com.campus.util.springboot.mybatisplus.PageDTO;
import com.campus.util.springboot.mybatisplus.PageUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 黄磊
 */
@Import(DataSourceAutoConfiguration.class)
@EnableMybatisPlus
@AutoConfigureMockMvc
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:p6spy:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MySQL",
        "spring.datasource.driver-class-name=com.p6spy.engine.spy.P6SpyDriver",
        "spring.datasource.username=sa",
})

public class MybatisPlusTests {
    @Autowired
    private MockMvc mvc;


    @Test
    @DisplayName("仅传递分页查询信息")
    void test_pageQuery1() throws Exception {
        mvc.perform(get("/mybatis-plus/page-query1")
                        .param("currentPage", "1")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"00000\",\"message\":\"\",\"data\":{\"currentPage\":1,\"pageSize\":1},\"errorMessage\":\"\"}", true));
    }

    @Test
    @DisplayName("传递分页+其他查询信息")
    void test_pageQuery2() throws Exception {
        mvc.perform(get("/mybatis-plus/page-query2")
                        .param("currentPage", "1")
                        .param("pageSize", "1")
                        .param("param1", "111"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"00000\",\"message\":\"\",\"data\":{\"currentPage\":1,\"pageSize\":1,\"param1\":\"111\"},\"errorMessage\":\"\"}", true));
    }

    @Test
    @DisplayName("PageDTO返回值测试")
    void test_pageDTO1() throws Exception {
        String result = mvc.perform(get("/mybatis-plus/page-dto1")
                        .param("currentPage", "1")
                        .param("pageSize", "1")
                        .param("param1", "111"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(JSONUtil.formatJsonStr(result));
    }

    @Test
    @DisplayName("替换PageDTO中的records")
    void test_pageUtil1() {
        PageDTO<String> stringPage = new PageDTO<>(1, 10, 2, List.of("123", "321", "111"));
        List<Integer> ints = stringPage.getRecords().stream().map(Integer::valueOf).toList();
        PageDTO<Integer> intPage = PageUtil.changeRecord(stringPage, ints);

        Assertions.assertThat(intPage.getCurrent()).isEqualTo(stringPage.getCurrent());
        Assertions.assertThat(intPage.getSize()).isEqualTo(stringPage.getSize());
        Assertions.assertThat(intPage.getTotal()).isEqualTo(stringPage.getTotal());
        Assertions.assertThat(intPage.getRecords()).isEqualTo(ints);
    }
}

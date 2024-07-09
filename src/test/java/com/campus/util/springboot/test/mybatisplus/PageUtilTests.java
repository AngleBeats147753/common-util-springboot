package com.campus.util.springboot.test.mybatisplus;

import com.campus.util.springboot.mybatisplus.OffsetPageDto;
import com.campus.util.springboot.mybatisplus.PageUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @author 黄磊
 */
public class PageUtilTests {
    @Test
    public void test_encode_decode() throws Exception {
        PageUtil.setCrypto("你好");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "123456");
        String encode = PageUtil.encode(map);
        JsonNode decode = PageUtil.decode(encode);
        Assertions.assertThat(decode.get("id").asText()).isEqualTo("123456");
    }

    @Test
    @DisplayName("替换PageDTO中的records")
    void test_pageUtil1() {
        OffsetPageDto<String> stringPage = new OffsetPageDto<>(1, 10, 2, List.of("123", "321", "111"));
        List<Integer> ints = stringPage.getRecords().stream().map(Integer::valueOf).toList();
        OffsetPageDto<Integer> intPage = PageUtil.changeRecord(stringPage, ints);

        Assertions.assertThat(intPage.getCurrent()).isEqualTo(stringPage.getCurrent());
        Assertions.assertThat(intPage.getSize()).isEqualTo(stringPage.getSize());
        Assertions.assertThat(intPage.getTotal()).isEqualTo(stringPage.getTotal());
        Assertions.assertThat(intPage.getRecords()).isEqualTo(ints);
    }
}

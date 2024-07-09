package com.campus.util.springboot.test.mybatisplus;

import com.campus.util.springboot.mybatisplus.OffsetPageQo;
import com.campus.util.springboot.mybatisplus.OffsetPageDto;
import com.eggcampus.util.result.ReturnResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 黄磊
 */
@RestController
public class MybatisPlusController {
    @GetMapping("/mybatis-plus/page-query1")
    public ReturnResult pageQuery1(OffsetPageQo query) {
        return ReturnResult.success(query);
    }

    @GetMapping("/mybatis-plus/page-query2")
    public ReturnResult pageQuery2(TestQuery query) {
        return ReturnResult.success(query);
    }

    @GetMapping("/mybatis-plus/page-dto1")
    public ReturnResult pageDTO1(OffsetPageQo query) {
        OffsetPageDto<Object> dto = new OffsetPageDto<>(query);
        dto.setRecords(List.of("123", "321"));
        return ReturnResult.success(dto);
    }
}

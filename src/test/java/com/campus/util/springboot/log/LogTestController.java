package com.campus.util.springboot.log;

import com.eggcampus.util.result.ReturnResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黄磊
 */
@RestController
public class LogTestController {
    @Log(value = "日志测试接口1")
    @GetMapping("/log/log1")
    public ReturnResult log1(LogQuery query) {
        return ReturnResult.getSuccessReturn("成功");
    }

    @Log(value = "日志测试接口2")
    @PostMapping("/log/log2")
    public ReturnResult log2(@Validated @RequestBody LogQuery query) {
        return ReturnResult.getSuccessReturn("成功");
    }

    @Log(value = "日志测试接口3")
    @PostMapping("/log/log3")
    public ReturnResult log3(LogQuery query) {
        return ReturnResult.getSuccessReturn("成功");
    }

    @Log(value = "日志测试接口4")
    @PostMapping("/log/log4")
    public ReturnResult log4(LogQuery query) {
        return ReturnResult.getSuccessReturn("成功");
    }

    @Log(value = "日志测试接口5")
    @GetMapping("/log/log5")
    public ReturnResult log5() {
        return ReturnResult.getSuccessReturn("成功");
    }
}

package com.campus.util.springboot.test.datetime;

import com.eggcampus.util.result.ReturnResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author 黄磊
 */
@RestController
public class DateTimeController {
    @GetMapping("/datetime/converter1")
    public ReturnResult converter1(LocalDateTime param1) {
        return ReturnResult.getSuccessReturn(param1);
    }

    @GetMapping("/datetime/converter2")
    public ReturnResult converter2(LocalDate param1) {
        return ReturnResult.getSuccessReturn(param1);
    }

    @GetMapping("/datetime/converter3")
    public ReturnResult converter3(LocalTime param1) {
        return ReturnResult.getSuccessReturn(param1);
    }

    @PostMapping("/datetime/converter5")
    public ReturnResult converter5(@RequestBody DateTimeConverterDTO dto) {
        return ReturnResult.getSuccessReturn(dto);
    }

    @PostMapping("/datetime/converter6")
    public ReturnResult converter6(@RequestBody DateConverterDTO dto) {
        return ReturnResult.getSuccessReturn(dto);
    }

    @PostMapping("/datetime/converter7")
    public ReturnResult converter7(@RequestBody TimeConverterDTO dto) {
        return ReturnResult.getSuccessReturn(dto);
    }

    @PostMapping("/datetime/converter8")
    public ReturnResult converter8(DateTimeConverterDTO dto) {
        return ReturnResult.getSuccessReturn(dto);
    }

    @PostMapping("/datetime/converter9")
    public ReturnResult converter9(DateConverterDTO dto) {
        return ReturnResult.getSuccessReturn(dto);
    }

    @PostMapping("/datetime/converter10")
    public ReturnResult converter10(TimeConverterDTO dto) {
        return ReturnResult.getSuccessReturn(dto);
    }
}

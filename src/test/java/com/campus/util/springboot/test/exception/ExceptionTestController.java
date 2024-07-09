package com.campus.util.springboot.test.exception;

import com.eggcampus.util.result.ReturnResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 黄磊
 */
@RestController
public class ExceptionTestController {

    @GetMapping("/exception/methodArgumentTypeMismatchException")
    public ReturnResult methodArgumentTypeMismatchException(Integer param1) {
        return ReturnResult.success(param1);
    }

    @GetMapping("/exception/missingServletRequestParameterException")
    public ReturnResult missingServletRequestParameterException(@RequestParam Integer param1) {
        return ReturnResult.success(param1);
    }

    @GetMapping("/exception/methodArgumentTypeMismatchException2/{id}")
    public ReturnResult methodArgumentTypeMismatchException2(@PathVariable("id") Long param1) {
        return ReturnResult.success(param1);
    }

    @PostMapping("/exception/methodArgumentTypeMismatchException3")
    public ReturnResult methodArgumentTypeMismatchException3(Integer param1) {
        return ReturnResult.success(param1);
    }

    @PostMapping("/exception/missingServletRequestParameterException3")
    public ReturnResult missingServletRequestParameterException3(@RequestParam Integer param1) {
        return ReturnResult.success(param1);
    }

    @PostMapping("/exception/httpMessageNotReadableException")
    public ReturnResult httpMessageNotReadableException(@RequestBody Exception1DTO dto) {
        return ReturnResult.success(dto);
    }

    @PostMapping("/exception/bindException")
    public ReturnResult aaa8(@RequestBody @Validated Exception3DTO dto) {
        return ReturnResult.success(dto);
    }

    @PostMapping("/exception/httpMessageNotReadableException2")
    public ReturnResult httpMessageNotReadableException2(@RequestBody Exception3DTO dto) {
        return ReturnResult.success(dto);
    }

}

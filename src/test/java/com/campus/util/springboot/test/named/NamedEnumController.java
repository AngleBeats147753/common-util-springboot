package com.campus.util.springboot.test.named;

import com.eggcampus.util.result.ReturnResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黄磊
 */
@RestController
public class NamedEnumController {
    @GetMapping("/enums/named1")
    public ReturnResult named1(NamedEnum1 param1) {
        return ReturnResult.success(param1);
    }

    @PostMapping("/enums/named2")
    public ReturnResult named2(@RequestBody NamedEnumDTO1 dto) {
        return ReturnResult.success(dto);
    }

    @PostMapping("/enums/named3")
    public ReturnResult named3(NamedEnum1 param1) {
        return ReturnResult.success(param1);
    }

    @PostMapping("/enums/named4")
    public ReturnResult named4(@RequestBody NamedEnumDTO2 dto) {
        return ReturnResult.success(dto);
    }

    @PostMapping("/enums/named5")
    public ReturnResult named5(@RequestBody NamedEnumDTO3 dto) {
        return ReturnResult.success(dto);
    }

    @PostMapping("/enums/named6")
    public ReturnResult named6(@RequestBody NamedEnumDTO4 dto) {
        return ReturnResult.success(dto);
    }

    @PostMapping("/enums/named7")
    public ReturnResult named7(@RequestBody NamedEnumDTO5 dto) {
        return ReturnResult.success(dto);
    }
}

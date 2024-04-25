package com.campus.util.springboot.mybatisplus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author 黄磊
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery {
    @NotNull(message = "currentPage不能为空")
    @Range(min = 1, max = 100, message = "页数应该在1~100之间")
    private Integer currentPage = 1;

    @NotNull(message = "pageSize不能为空")
    @Range(min = 1, max = 100, message = "一页元素数量应该在1~100之间")
    private Integer pageSize = 10;
}

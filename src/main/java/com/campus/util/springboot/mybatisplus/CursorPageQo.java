package com.campus.util.springboot.mybatisplus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * 游标分页查询参数
 *
 * @author 黄磊
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursorPageQo {
    private String cursorId;

    @NotNull(message = "pageSize不能为空")
    @Range(min = 1, max = 100, message = "一页元素数量应该在1~100之间")
    private Integer pageSize = 10;

    @Nullable
    public String getCursorId() {
        return cursorId;
    }
}

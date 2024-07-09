package com.campus.util.springboot.mybatisplus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 游标分页返回数据
 *
 * @author 黄磊
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursorPageDto<T> {
    private Collection<T> records;
    /**
     * 请求下一页时所需要的cursorId
     */
    private String next;
}

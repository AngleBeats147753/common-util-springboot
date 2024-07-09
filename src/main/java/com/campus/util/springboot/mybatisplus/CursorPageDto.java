package com.campus.util.springboot.mybatisplus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 游标分页返回数据
 *
 * @author 黄磊
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursorPageDto<T> {
    public static final String END_CURSOR_ID = "END";
    private Collection<T> records;
    /**
     * 请求下一页时所需要的cursorId
     */
    private String next;

    public static <T> CursorPageDto<T> of(Collection<T> records, CursorPageQo pageQo, Function<T, Map<String, Object>> next) {
        List<T> list = records.stream().toList();
        if (list.isEmpty() || list.size() < pageQo.getPageSize()) {
            return new CursorPageDto<>(records, END_CURSOR_ID);
        } else {
            Map<String, Object> cursorParam = next.apply(list.get(list.size() - 1));
            return new CursorPageDto<>(records, PageUtil.encode(cursorParam));
        }
    }
}

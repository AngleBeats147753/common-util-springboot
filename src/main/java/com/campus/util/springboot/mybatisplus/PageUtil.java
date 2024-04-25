package com.campus.util.springboot.mybatisplus;

import java.util.List;

/**
 * @author 黄磊
 */
public class PageUtil {
    /**
     * 从旧的PageDTO中提取分页信息，将records替换为新的records
     *
     * @param oldPageDTO 旧的PageDTO
     * @param records    新的records
     * @param <T>        records的类型
     * @return 新的PageDTO
     */
    public static <T> PageDTO<T> changeRecord(PageDTO<?> oldPageDTO, List<T> records) {
        return new PageDTO<>(oldPageDTO.getCurrent(), oldPageDTO.getSize(), oldPageDTO.getTotal(), records);
    }
}

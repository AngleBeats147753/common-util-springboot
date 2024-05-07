package com.campus.util.springboot.test.mybatisplus;

import com.campus.util.springboot.mybatisplus.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 黄磊
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestQuery extends PageQuery {
    private String param1;
}

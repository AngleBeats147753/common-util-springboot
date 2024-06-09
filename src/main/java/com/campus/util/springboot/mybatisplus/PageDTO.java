package com.campus.util.springboot.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author 黄磊
 **/
public class PageDTO<T> extends Page<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public PageDTO() {
    }

    public PageDTO(long current, long size) {
        super(current, size);
    }

    public PageDTO(long current, long size, long total) {
        super(current, size, total);
    }

    public PageDTO(long current, long size, boolean searchCount) {
        super(current, size, searchCount);
    }

    public PageDTO(long current, long size, long total, boolean searchCount) {
        super(current, size, total, searchCount);
    }

    public PageDTO(PageQuery query) {
        super(query.getCurrentPage(), query.getPageSize());
    }

    public PageDTO(PageQo qo) {
        super(qo.getCurrentPage(), qo.getPageSize());
    }

    public PageDTO(long current, long size, long total, List<T> records) {
        super(current, size, total);
        this.records = records;
    }

    @Override
    @JsonIgnore
    public long getTotal() {
        return super.getTotal();
    }

    @Override
    @JsonIgnore
    public long getSize() {
        return super.getSize();
    }

    @Override
    @JsonIgnore
    public long getCurrent() {
        return super.getCurrent();
    }

    @Deprecated
    @JsonIgnore
    public String getCountId() {
        return this.countId;
    }

    @Deprecated
    @JsonIgnore
    public Long getMaxLimit() {
        return this.maxLimit;
    }

    @Deprecated
    @JsonIgnore
    public List<OrderItem> getOrders() {
        return this.orders;
    }

    @Deprecated
    @JsonIgnore
    public boolean isOptimizeCountSql() {
        return this.optimizeCountSql;
    }

    @Deprecated
    @JsonIgnore
    public boolean isSearchCount() {
        return this.searchCount;
    }
}

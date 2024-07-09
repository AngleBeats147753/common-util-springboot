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
public class OffsetPageDto<T> extends Page<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public OffsetPageDto() {
    }

    public OffsetPageDto(long current, long size) {
        super(current, size);
    }

    public OffsetPageDto(long current, long size, long total) {
        super(current, size, total);
    }

    public OffsetPageDto(long current, long size, boolean searchCount) {
        super(current, size, searchCount);
    }

    public OffsetPageDto(long current, long size, long total, boolean searchCount) {
        super(current, size, total, searchCount);
    }

    public OffsetPageDto(OffsetPageQo qo) {
        super(qo.getCurrentPage(), qo.getPageSize());
    }

    public OffsetPageDto(long current, long size, long total, List<T> records) {
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

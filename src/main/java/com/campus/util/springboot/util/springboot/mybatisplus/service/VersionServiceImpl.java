package com.campus.util.springboot.util.springboot.mybatisplus.service;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.util.springboot.util.springboot.mybatisplus.exception.UpdateFailureException;
import com.eggcampus.util.exception.database.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 改进的ServiceImpl，实现了以下功能 <br>
 * 1. 支持乐观锁更新失败时重试机制 <br>
 *
 * @author 黄磊
 */
@Slf4j
public class VersionServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    private Integer maxRetryTimes;

    @Resource
    TransactionTemplate transactionTemplate;

    @Override
    public boolean updateById(T entity) {
        PropertyDescriptor idDescriptor = BeanUtils.getPropertyDescriptor(entity.getClass(), "id");
        PropertyDescriptor versionDescriptor = BeanUtils.getPropertyDescriptor(entity.getClass(), "version");
        if (Objects.isNull(idDescriptor) || Objects.isNull(versionDescriptor)) {
            return super.updateById(entity);
        }
        Long id;
        try {
            id = (Long) idDescriptor.getReadMethod().invoke(entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        transactionTemplate.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
        transactionTemplate.setIsolationLevel(Isolation.READ_COMMITTED.value());
        Boolean execute = transactionTemplate.execute(status -> {
            try {
                for (int i = 0; i <= this.maxRetryTimes; i++) {
                    if (i != 0) {
                        log.debug("乐观锁更新失败，开始第{}次重试", i);
                        Thread.sleep(20 + RandomUtil.randomInt(0, 20));
                    }
                    T newestEntity = getById(id);
                    if (Objects.isNull(newestEntity)) {
                        throw new UpdateFailureException("更新失败，数据不存在");
                    }
                    Long oldVersion = (Long) versionDescriptor.getReadMethod().invoke(newestEntity);
                    versionDescriptor.getWriteMethod().invoke(entity, oldVersion);
                    if (super.updateById(entity)) {
                        return true;
                    }
                }
                throw new OptimisticLockException("更新失败，重试%d次后仍然失败".formatted(this.maxRetryTimes));
            } catch (InterruptedException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return BooleanUtil.isTrue(execute);
    }


    @Value("${mybatis-plus.optimistic-locking.max-retry-times:5}")
    private void setMaxRetryTimes(int maxRetryTimes) {
        if (maxRetryTimes < 0) {
            throw new IllegalArgumentException("maxRetryTimes must be at least 1");
        }
        this.maxRetryTimes = maxRetryTimes;
    }

}

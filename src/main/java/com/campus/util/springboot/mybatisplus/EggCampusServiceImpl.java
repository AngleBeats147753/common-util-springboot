package com.campus.util.springboot.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eggcampus.util.exception.database.OptimisticLockException;

/**
 * 旦晓园团队自定义 Service 实现类
 * <p>
 * 对于ServiceImpl的方法进行了以下修改
 *    <ul>
 *        <li>修改了单个对象插入/修改的update、updateById和saveOrUpdate方法。假如没有行受影响，那么抛出异常，而不是返回false</li>
 *    </ul>
 * </p>
 *
 * @author 黄磊
 */
public class EggCampusServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    private static final String ERROR_MESSAGE_UPDATE_FAILURE = "更新失败";

    @Override
    public boolean updateById(T entity) {
        boolean isSuccess = super.updateById(entity);
        if (!isSuccess) {
            throw new OptimisticLockException(ERROR_MESSAGE_UPDATE_FAILURE);
        }
        return true;
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        boolean isSuccess = super.update(entity, updateWrapper);
        if (!isSuccess) {
            throw new OptimisticLockException(ERROR_MESSAGE_UPDATE_FAILURE);
        }
        return true;
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        boolean isSuccess = super.saveOrUpdate(entity);
        if (!isSuccess) {
            throw new OptimisticLockException(ERROR_MESSAGE_UPDATE_FAILURE);
        }
        return true;
    }

    @Override
    public boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper) {
        boolean isSuccess = super.saveOrUpdate(entity, updateWrapper);
        if (!isSuccess) {
            throw new OptimisticLockException(ERROR_MESSAGE_UPDATE_FAILURE);
        }
        return true;
    }
}

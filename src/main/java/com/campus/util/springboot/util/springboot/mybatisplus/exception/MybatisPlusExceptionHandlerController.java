package com.campus.util.springboot.util.springboot.mybatisplus.exception;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 黄磊
 */
@Slf4j
@Order(1)
@RestControllerAdvice
public class MybatisPlusExceptionHandlerController implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化成功！！！");
    }

    /**
     * 乐观锁重试超过上限
     */
    @ExceptionHandler(value = OptimisticLockException.class)
    public ReturnResult handleOptimisticLockException(OptimisticLockException e) {
        log.error("乐观锁重试次数超过上限", e);
        return ReturnResult.getFailureReturn(AliErrorCode.SYSTEM_ERROR_B0102, "系统繁忙中，请10分钟后再尝试", e.getLocalizedMessage());
    }

    /**
     * 未找到数据
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ReturnResult handleNotFoundException(NotFoundException e) {
        return ReturnResult.getFailureReturn(AliErrorCode.USER_ERROR_A0402, e.getLocalizedMessage());
    }

    @ExceptionHandler(value = UpdateFailureException.class)
    public ReturnResult handleUpdateFailureException(UpdateFailureException e) {
        log.error("数据更新失败", e);
        return ReturnResult.getFailureReturn(AliErrorCode.SYSTEM_ERROR_B0001, e.getLocalizedMessage(), e.getLocalizedMessage());
    }

    @ExceptionHandler(value = MybatisPlusException.class)
    public ReturnResult handleMybatisPlusException(MybatisPlusException e) {
        log.error("数据库执行出错", e);
        return ReturnResult.getFailureReturn(AliErrorCode.SYSTEM_ERROR_B0001, e.getLocalizedMessage(), e.getLocalizedMessage());
    }
}

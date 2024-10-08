package com.campus.util.springboot.mybatisplus;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.campus.util.springboot.exception.ErrorMessage;
import com.eggcampus.util.exception.EggCampusException;
import com.eggcampus.util.exception.database.DatabaseException;
import com.eggcampus.util.exception.database.NotFoundException;
import com.eggcampus.util.exception.database.OptimisticLockException;
import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 黄磊
 */
@Slf4j
@Order(999)
@RestControllerAdvice
public class MybatisPlusExceptionHandlerController {
    /**
     * 乐观锁重试超过上限
     */
    @ExceptionHandler(value = OptimisticLockException.class)
    public ReturnResult handleOptimisticLockException(OptimisticLockException e) {
        log.error("乐观锁重试次数超过上限", e);
        return ReturnResult.failure(AliErrorCode.SYSTEM_ERROR_B0102, ErrorMessage.BUSY_TIP, e.getLocalizedMessage());
    }

    /**
     * 未找到数据
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ReturnResult handleNotFoundException(NotFoundException e) {
        log.debug("未找到数据", e);
        return ReturnResult.failure(AliErrorCode.USER_ERROR_A0402, e.getLocalizedMessage());
    }

    @ExceptionHandler(value = OutOfPaginationException.class)
    public ReturnResult handleOutOfPaginationException(OutOfPaginationException e) {
        log.debug("超出分页范围", e);
        return ReturnResult.failure(AliErrorCode.USER_ERROR_A0402, e.getLocalizedMessage());
    }

    @ExceptionHandler(value = DatabaseException.class)
    public ReturnResult handleDatabaseException(EggCampusException e) {
        log.error("操作数据库异常", e);
        return ReturnResult.failure(AliErrorCode.SYSTEM_ERROR_B0001, ErrorMessage.UNKNOWN_TIP, e.getLocalizedMessage());
    }

    @ExceptionHandler(value = MybatisPlusException.class)
    public ReturnResult handleMybatisPlusException(MybatisPlusException e) {
        log.error("操作数据库异常", e);
        return ReturnResult.failure(AliErrorCode.SYSTEM_ERROR_B0001, e.getLocalizedMessage(), e.getLocalizedMessage());
    }
}

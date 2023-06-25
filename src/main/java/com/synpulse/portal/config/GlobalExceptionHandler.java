package com.synpulse.portal.config;

import com.synpulse.portal.dto.Result;
import com.synpulse.portal.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception interceptor
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            return Result.fail(be.getCode(),be.getMsg());
        }

        log.error("service exception:{}",e.getMessage());

        return Result.fail();
    }
}

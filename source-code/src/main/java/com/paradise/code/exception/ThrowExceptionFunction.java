package com.paradise.code.exception;

import com.paradise.code.constant.ErrCodeEnum;

/**
 * 抛异常接口
 **/
@FunctionalInterface
public interface ThrowExceptionFunction {

    /**
     * 抛出异常信息
     *
     * @param errCodeEnum 异常编码
     **/
    void throwErrorCode(ErrCodeEnum errCodeEnum);

}
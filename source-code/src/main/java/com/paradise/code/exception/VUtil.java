package com.paradise.code.exception;

import java.util.Collection;

public class VUtil {

    /**
     * 如果参数为true抛出异常
     *
     * @param b judge flag
     * @return com.example.demo.func.ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction isTure(boolean b) {

        return (errCodeEnum) -> {
            if (b) {
                throw new RuntimeException(errCodeEnum.getErrMsg());
            }
        };
    }

    /**
     * 如果参数为空抛出异常
     *
     * @param t 参数对象
     * @return com.example.demo.func.ThrowExceptionFunction
     **/
    public static <T> ThrowExceptionFunction requireNoNull(T t) {

        return (errCodeEnum) -> {
            if (null == t) {
                throw new NullPointerException(errCodeEnum.getErrMsg());
            }
        };
    }

    /**
     * 如果集合为空抛出异常
     *
     * @param collection 集合
     * @return com.example.demo.func.ThrowExceptionFunction
     **/
    public static <T> ThrowExceptionFunction requireNotEmpty(Collection<T> collection) {

        return (errCodeEnum) -> {
            if (null == collection || collection.isEmpty()) {
                throw new RuntimeException(errCodeEnum.getErrMsg());
            }
        };
    }
}

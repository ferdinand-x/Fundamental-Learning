package com.paradise.code.util.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author : PARADISE
 * @InterfaceName : IBaseEnum
 * @description :
 * @since : 2023/10/25 23:15
 */
@SuppressWarnings("all")
public interface IBaseEnum<T> {

    default T getCode() {
        return EnumHolder.getEnumBean(this).getCode();
    }

    default String getMsg() {
        return EnumHolder.getEnumBean(this).getText();
    }

    default void initEnum(T code, String msg) {
        EnumHolder.putEnum(this, code, msg);
    }

    static <T, R extends IBaseEnum<T>> R getByType(Class<? extends IBaseEnum<T>> clazz, T code) {
        return Arrays.stream(clazz.getEnumConstants())
                .filter(baseEnum -> Objects.equals(baseEnum.getCode(), code))
                .map(baseEnum -> (R) baseEnum)
                .findAny().orElse(null);
    }

    class EnumHolder {

        private static final Map<? super IBaseEnum<?>, EnumBean> ENUM_MAP = Maps.newConcurrentMap();

        public static <T> void putEnum(IBaseEnum<T> baseEnum, T code, String msg) {
            ENUM_MAP.putIfAbsent(baseEnum, new EnumBean<>(baseEnum.getCode(), baseEnum.getMsg()));
        }

        public static <K extends IBaseEnum<T>, T> EnumBean<T> getEnumBean(K enumKey) {
            return ENUM_MAP.get(enumKey);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class EnumBean<T> {

        private T code;

        private String text;
    }
}

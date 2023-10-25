package com.paradise.code.util.enums;

import lombok.Getter;

/**
 * @author : PARADISE
 * @ClassName : StateCode
 * @description :
 * @since : 2023/10/25 23:50
 */
@Getter
public enum StateCode implements IBaseEnum<Integer> {

    INIT(0, "INIT"),
    PREPARE(1, "PREPARE"),
    READY(2, "READY"),
    START(3, "START"),
    RUN(4, "RUN"),
    STOP(5, "STOP"),
    DIE(5, "DIE");

    private final int code;

    private final String msg;

    StateCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        initEnum(code, msg);
    }
}

package com.paradise.code.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
@Getter
public enum ErrCodeEnum {

    TASKS_REQUIRE_NOT_EMPTY(9000,"tasks require not empty"),
    EXECUTOR_NO_NULL(9001,"executor require no null"),
    TIMEOUT_ILLEGAL_PARAM(9002,"timeout parameter don't <= 0");

    private final int code;
    private final String errMsg;


}

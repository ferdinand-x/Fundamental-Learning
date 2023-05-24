package com.paradise.code.pojo.bo;

import lombok.Data;

import java.time.LocalTime;

/**
 * @author : PARADISE
 * @ClassName : CallInfo
 * @description : api hit info
 * @since : 2023/3/26 17:11
 */
@Data
public class CallInfo {

    /**
     * call time,eg:{@code 20230326 17:13}
     */
    private LocalTime datetime;

    /**
     * run time(duration)
     */
    private Long rt;

    /**
     * whether hit the cache
     */
    private boolean isCacheHit;
}

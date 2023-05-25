package com.paradise.code.pojo.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author PARADISE
 */
@Data
@Accessors(chain = true)
public class HitRateBO {

    private AtomicInteger totalCall = new AtomicInteger();

    private AtomicInteger cacheHit = new AtomicInteger();
}

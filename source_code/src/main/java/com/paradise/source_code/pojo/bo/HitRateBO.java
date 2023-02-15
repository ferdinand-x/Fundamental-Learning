package com.paradise.source_code.pojo.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Accessors(chain = true)
public class HitRateBO {

    private AtomicInteger hit;
}

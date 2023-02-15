package com.paradise.source_code.service;

import com.paradise.source_code.pojo.bo.HitRateBO;
import com.paradise.source_code.process.CachePostProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HitRateService {

    private final CachePostProcessor cachePostProcessor;

    public HitRateBO getHitRate(){
        return cachePostProcessor.getHitRate();
    }
}

package com.paradise.code.process;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.paradise.code.annotation.ProcessOrder;
import com.paradise.code.pojo.bo.HitRateBO;
import com.paradise.code.pojo.bo.RenderBO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author PARADISE
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Getter
@ProcessOrder(before = Integer.MIN_VALUE + 1, after = Integer.MAX_VALUE)
public class CachePostProcessor implements TextRenderPostProcessor {

    private static final Cache<String, String> LOCAL_CACHE = CacheBuilder.newBuilder()
            // init capacity 30
            .initialCapacity(30)
            // concurrency level 2
            .concurrencyLevel(2)
            // set the expiration after write
            .expireAfterWrite(2, TimeUnit.MINUTES)
            // new instance
            .build();

    private final HitRateBO hitRate = new HitRateBO();

    @Override
    public boolean handleBefore(PostContext<RenderBO> postContext) {
        RenderBO bizData = postContext.getBizData();
        String cacheText = LOCAL_CACHE.getIfPresent(bizData.getTextKey());
        // increment view count.
        hitRate.getCacheHit().incrementAndGet();
        if (StringUtils.hasText(cacheText)) {
            return true;
        }
        // increment cache-hit count.
        hitRate.getCacheHit().incrementAndGet();
        bizData.setText(cacheText);
        return false;
    }

    @Override
    public void handleAfter(PostContext<RenderBO> postContext) {
        RenderBO bizData = postContext.getBizData();
        Optional.ofNullable(bizData)
                .ifPresent(v -> LOCAL_CACHE.put(v.getTextKey(), v.getText()));
    }

}

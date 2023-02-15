package com.paradise.source_code.process;

import com.paradise.source_code.annotation.ProcessOrder;
import com.paradise.source_code.config.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * the container for interface processor.
 *
 * @param <T>
 */
@Component
@Slf4j
public class PostProcessorContainer<T> {

    private static final int BEFORE_DEFAULT_ORDER = Integer.MAX_VALUE;

    private static final int AFTER_DEFAULT_ORDER = Integer.MIN_VALUE;

    /**
     * this spring util.
     */
    private ApplicationContextHolder applicationContextHolder;

    /**
     * require no instance with non-args constructor.
     */
    private PostProcessorContainer() {
    }

    /**
     * all-args constructor.
     */
    @Autowired
    public PostProcessorContainer(ApplicationContextHolder applicationContextHolder) {
        this.applicationContextHolder = applicationContextHolder;
    }

    public boolean handleBefore(PostContext<T> postContext) {
        boolean isContinue = false;
        Collection<BasePostProcessor<T>> processors = applicationContextHolder.getBeans(BasePostProcessor.class);
        if (CollectionUtils.isEmpty(processors)) {
            return false;
        }
        List<BasePostProcessor<T>> sortedProcessors = processors.stream()
                .sorted(Comparator.comparingInt(this::getBeforeOrder))
                .collect(Collectors.toList());
        for (BasePostProcessor<T> processor : sortedProcessors) {
             isContinue = processor.handleBefore(postContext);
        }
        return isContinue;
    }

    public void handleAfter(PostContext<T> postContext) {
        Collection<BasePostProcessor<T>> processors = applicationContextHolder.getBeans(BasePostProcessor.class);
        if (CollectionUtils.isEmpty(processors)) {
            return;
        }
        processors.stream()
                .sorted(Comparator.comparingInt(this::getAfterOrder))
                .forEach(v -> v.handleAfter(postContext));
    }

    private int getBeforeOrder(BasePostProcessor<? super T> postProcessor) {
        Objects.requireNonNull(postProcessor);
        ProcessOrder processOrder = AnnotationUtils.findAnnotation(postProcessor.getClass(), ProcessOrder.class);
        return Optional.ofNullable(processOrder)
                .map(ProcessOrder::before).orElse(BEFORE_DEFAULT_ORDER);
    }


    private int getAfterOrder(BasePostProcessor<? super T> postProcessor) {
        Objects.requireNonNull(postProcessor);
        ProcessOrder processOrder = AnnotationUtils.findAnnotation(postProcessor.getClass(), ProcessOrder.class);
        return Optional.ofNullable(processOrder)
                .map(ProcessOrder::after).orElse(AFTER_DEFAULT_ORDER);
    }
}

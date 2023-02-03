package com.paradise.source_code.process;

import com.paradise.source_code.config.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Comparator;

/**
 * the container for interface processor.
 *
 * @param <T>
 */
@Component
@Slf4j
public class PostProcessorContainer<T> {

    /**
     * class for interface postprocessor.
     */
    private Class<BasePostProcessor> monitorPostProcessorClass = BasePostProcessor.class;

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

    @SuppressWarnings("unchecked")
    public boolean handleBefore(PostContext<T> postContext) {
        Collection<? extends BasePostProcessor> processors = applicationContextHolder.getBeans(BasePostProcessor.class);
        if (CollectionUtils.isEmpty(processors)) {
            return false;
        }
        processors.stream()
                .sorted(Comparator.comparingInt(BasePostProcessor::getPriority))
                .forEach(v -> v.handleBefore(postContext));
        return true;
    }

    @SuppressWarnings("unchecked")
    public void handleAfter(PostContext<T> postContext) {
        Collection<? extends BasePostProcessor> processors = applicationContextHolder.getBeans(BasePostProcessor.class);
        if (CollectionUtils.isEmpty(processors)) {
            return;
        }
        processors.stream()
                .sorted(Comparator.comparingInt(BasePostProcessor::getPriority))
                .forEach(v -> v.handleAfter(postContext));
    }
}

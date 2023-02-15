package com.paradise.source_code.config;

import com.paradise.source_code.process.BasePostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * the spring application util
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName) {
        return (T) this.applicationContext.getBean(beanName);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public <T> Collection<T> getBeans(Class<BasePostProcessor> tClass) {
        return (Collection<T>) this.applicationContext.getBeansOfType(tClass).values();
    }

    public <T> T getBean(String beanName, Class<T> tClass) {
        return this.applicationContext.getBean(beanName, tClass);
    }
}

package com.paradise.code.annotation;

import java.lang.annotation.*;

/**
 * obtain the priority for process.
 * for the high priority with min value will be return.
 * @author PARADISE
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessOrder {

    /**
     * for the handle before process priority.
     *
     * @return the order for process.
     */
    int before() default Integer.MAX_VALUE;

    /**
     * for the handle after process priority.
     *
     * @return the order for process.
     */
    int after() default Integer.MIN_VALUE;
}

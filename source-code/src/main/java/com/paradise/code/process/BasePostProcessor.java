package com.paradise.code.process;

/**
 * the extra inter for process
 *
 * @param <T>
 */
public interface BasePostProcessor<T> {

    /**
     * handle with prepare process.like: auth security or loading config properties.
     *
     * @param postContext the container for business entity.
     * @return it is necessary for main process.
     */
    default boolean handleBefore(PostContext<T> postContext) {
        return true;
    }

    /**
     * handle with post process.like: data processing
     * it is unnecessary to do something.
     *
     * @param postContext the container for business entity.
     */
    default void handleAfter(PostContext<T> postContext) {

    }

}

package com.paradise.code.util;

import com.paradise.code.constant.ErrCodeEnum;
import com.paradise.code.exception.VUtil;
import com.paradise.code.pojo.dto.BaseRspDTO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author PARADISE
 */
@UtilityClass
public class ExecutorCompletionServiceUtil {

    public static<T extends BaseRspDTO<T>> Collection<T> executeTask(Collection<Callable<T>> tasks, long timeOut, ExecutorService executor) {

        // check params
        VUtil.isTure(timeOut <= 0).throwErrorCode(ErrCodeEnum.TIMEOUT_ILLEGAL_PARAM);
        VUtil.requireNotEmpty(tasks).throwErrorCode(ErrCodeEnum.TASKS_REQUIRE_NOT_EMPTY);
        VUtil.requireNoNull(executor).throwErrorCode(ErrCodeEnum.EXECUTOR_NO_NULL);

        List<T> results = new ArrayList<>(tasks.size());
        // submit task
        CompletionService<T> completionService = new ExecutorCompletionService<>(executor);
        tasks.forEach(completionService::submit);

        try {
            // transfer to result
            for (int i = 0; i < tasks.size(); i++) {
                Future<T> future = completionService.poll(timeOut, TimeUnit.SECONDS);
                results.add(future.get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e){
            // ignore.
        }

        return results;
    }
}

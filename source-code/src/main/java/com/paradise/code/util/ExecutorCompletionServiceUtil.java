package com.paradise.code.util;

import com.google.common.collect.Lists;
import com.paradise.code.constant.ErrCodeEnum;
import com.paradise.code.exception.VUtil;
import com.paradise.code.pojo.dto.BaseRspDTO;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.concurrent.*;

@UtilityClass
public class ExecutorCompletionServiceUtil {

    public static<T extends BaseRspDTO<T>> Collection<T> executeTask(Collection<Callable<T>> tasks, long timeOut, ExecutorService executor) {

        // check params
        VUtil.isTure(timeOut <= 0).throwErrorCode(ErrCodeEnum.TIMEOUT_ILLEGAL_PARAM);
        VUtil.requireNotEmpty(tasks).throwErrorCode(ErrCodeEnum.TASKS_REQUIRE_NOT_EMPTY);
        VUtil.requireNoNull(executor).throwErrorCode(ErrCodeEnum.EXECUTOR_NO_NULL);

        Collection<T> results = Lists.newArrayList();
        // submit task
        CompletionService<T> completionService = new ExecutorCompletionService<>(executor);
//        for (Callable<BaseRspDTO<Object>> task : taskList) {
//            completionService.submit(task);
//        }
        tasks.forEach(completionService::submit);

        try {
            // transfer to result
            for (int i = 0; i < tasks.size(); i++) {
                Future<T> baseRspDTOFuture = completionService.poll(timeOut, TimeUnit.SECONDS);
                results.add(baseRspDTOFuture.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return results;
    }
}

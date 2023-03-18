package com.paradise.multithread.chapter1.return_result;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : PARADISE
 * @ClassName : AccumCallableTest
 * @description : 测试通过实现Callable {@link AccumCallable}接口获取线程任务返回值
 * @since : 2023/3/18 22:09
 */
public class AccumCallableTest {

    public static void main(String[] args) {

        // 创建10个线程,每个线程分别负责累加1~10,11~20,21~30..91~100
        List<FutureTask<Integer>> callables = IntStream.range(0, 10)
                .boxed()
                .map(AccumCallableTest::callableTask)
                .collect(Collectors.toList());
        // 获取线程结果集
        Integer accumResult = callables.stream()
                .map(AccumCallableTest::callableResult)
                .reduce(Integer::sum).orElse(0);
        // 获取累计结果
        System.out.printf("累计最终结果[%d]\n",accumResult);
    }

    private static Integer callableResult(FutureTask<Integer> futureTask) {
        Integer result = null;
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            // ignore
            result = 0;
            System.err.printf("获取FutureTask结果异常:%s\n",e);
        }
        return result;
    }

    private static FutureTask<Integer> callableTask(Integer range) {
        AccumCallable accumCallable = new AccumCallable(range * 10 + 1, (range + 1) * 10);
        FutureTask<Integer> futureTask = new FutureTask<>(accumCallable);
        Thread thread = new Thread(futureTask, String.format("累加线程[%d]", range));
        thread.start();
        return futureTask;
    }
}

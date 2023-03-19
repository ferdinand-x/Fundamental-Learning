package com.paradise.multithread.chapter2.thread_pool;

import com.paradise.multithread.chapter1.return_result.AccumCallable;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : PARADISE
 * @ClassName : CallableTest
 * @description : 使用线程池测试Callable接口{@link AccumCallable},获取最终结果
 * @since : 2023/3/18 23:02
 */
public class CallableTest2 {

    /**
     * 设备核心数
     */
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        // 创建定长线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
        // 创建并执行任务,获取结果
        List<AccumCallable> tasks = IntStream.range(0, 10)
                .boxed()
                .map(CallableTest2::callableTask)
                .collect(Collectors.toList());
        // 使用invokeAll() 阻塞获取所有线程的执行结果
        Integer accumResult = null;
        try {
            accumResult = threadPool.invokeAll(tasks).stream()
                    .map(CallableTest2::callableResult)
                    .reduce(Integer::sum).orElse(0);
            // 发送关闭线程池指令
            threadPool.shutdown();
            // 等待线程池关闭,设置最大等待时间为30分钟
            threadPool.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // ignore
            System.err.printf("线程池关闭失败:%s",e);
        }
        System.out.printf("累计最终结果[%d]", accumResult);
    }

    private static Integer callableResult(Future<Integer> future) {
        Integer result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            // ignore
        }
        return result;
    }

    private static AccumCallable callableTask(Integer range) {
        return new AccumCallable(range * 10 + 1, (range + 1) * 10);
    }
}

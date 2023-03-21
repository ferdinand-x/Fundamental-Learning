package com.paradise.multithread.chapter2.thread_pool;

import com.paradise.multithread.chapter1.return_result.AccumRunnable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : PARADISE
 * @ClassName : RunnableTest
 * @description : 使用线程池测试Runnable接口 {@link AccumRunnable}类型的任务
 * @since : 2023/3/18 22:43
 */
public class RunnableTest {

    /**
     * 设备核心数
     */
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        // 创建线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
        // 执行任务
        List<AccumRunnable> tasks = IntStream.range(0, 10)
                .boxed()
                .map(v -> RunnableTest.runnableTask(v, fixedThreadPool))
                .collect(Collectors.toList());
        // 发送关闭线程池指令
        fixedThreadPool.shutdown();
        try {
            // 等待线程池关闭,设置最大等待时间为30分钟
            fixedThreadPool.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // ignore
            System.err.printf("线程池关闭失败:%s",e);
        }
        // 获取结果集
        Integer accumResult = tasks.stream()
                .map(AccumRunnable::getResult)
                .reduce(Integer::sum).orElse(0);
        System.out.printf("累计最终结果[%d]",accumResult);
    }

    private static AccumRunnable runnableTask(Integer range, ExecutorService executorService) {
        AccumRunnable accumRunnable = new AccumRunnable(range * 10 + 1, (range + 1) * 10);
        // 执行任务
        executorService.execute(accumRunnable);
        return accumRunnable;
    }
}

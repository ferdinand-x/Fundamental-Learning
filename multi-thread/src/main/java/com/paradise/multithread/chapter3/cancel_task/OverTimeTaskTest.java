package com.paradise.multithread.chapter3.cancel_task;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author : PARADISE
 * @ClassName : OverTimeTaskTest
 * @description : 测试取消超时任务 {@link OverTimeTask}
 * @since : 2023/3/19 15:27
 */
public class OverTimeTaskTest {

    public static void main(String[] args) {
        // 定义线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        // 创建多个任务
        OverTimeTask task1 = new OverTimeTask(1);
        OverTimeTask task2 = new OverTimeTask(1_000);
        OverTimeTask task3 = new OverTimeTask(4_000);
        OverTimeTask task4 = new OverTimeTask(5_000);
        OverTimeTask task5 = new OverTimeTask(10_000);
        OverTimeTask task6 = new OverTimeTask(20_000);
        List<OverTimeTask> tasks = List.of(task1, task2, task3, task4, task5, task6);

        // 定义n秒后取消任务
        tasks.stream()
                .map(threadPool::submit)
                .forEach(v -> cancelTask(v, 1000));
        cancelTask(threadPool.submit(task3), 1000);
        // 关闭线程池
        try {
            threadPool.shutdownNow();
            threadPool.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    /**
     * 取消任务实现
     *
     * @param future future对象
     * @param delay  延时取消时长
     * @param <V>    future返回值类型
     * @return 取消结果, 成功为true, 失败为false
     */
    private static <V> boolean cancelTask(final Future<V> future, final int delay) {

        Callable<Boolean> callable = () -> {
            boolean innerResult = false;
            try {
                // 指定时间后取消任务
                Thread.sleep(delay);
                future.cancel(true);
                System.out.printf("[%s]线程任务取消成功\n\n", Thread.currentThread().getName());
                innerResult = true;
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            return innerResult;
        };
        FutureTask<Boolean> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        boolean result = false;
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            // ignore
        }
        return result;
    }
}

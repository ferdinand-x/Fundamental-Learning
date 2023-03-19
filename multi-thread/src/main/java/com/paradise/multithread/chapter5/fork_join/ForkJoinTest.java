package com.paradise.multithread.chapter5.fork_join;

import java.util.concurrent.*;

/**
 * @author : PARADISE
 * @ClassName : ForkJoinTest
 * @description : 测试fork-join计算π任务 {@link PiEstimateTask}
 * @since : 2023/3/19 20:56
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        // 创建Fork-Join-Pool
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        // 创建compute π任务对象
        // 计算1000亿项,分割临界值为1千万
        PiEstimateTask piEstimateTask = new PiEstimateTask(0, 100_000_000_000L, 1_000_000L);
        // 阻塞执行任务
        Double pi = forkJoinPool.invoke(piEstimateTask);
        System.out.printf("[阻塞执行]π的计算结果为[%s]\n",pi);
        // 非阻塞执行任务
        ForkJoinTask<Double> forkJoinTask = forkJoinPool.submit(piEstimateTask);
        try {
            System.out.printf("[非阻塞执行]π的计算结果为[%s],[forkJoinTask]是否指向[piEstimateTask]:[%s]\n",forkJoinTask.get(),forkJoinTask == piEstimateTask);
        } catch (InterruptedException | ExecutionException e) {
            // ignore
        }
        // 关闭线程池
        try {
            forkJoinPool.shutdown();
            forkJoinPool.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}

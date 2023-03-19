package com.paradise.multithread.chapter3.cancel_task;

import java.util.concurrent.*;

/**
 * @author : PARADISE
 * @ClassName : PrimerTaskTest
 * @description : 测试素数任务 {@link PrimerTask}
 * @since : 2023/3/19 10:39
 */
public class PrimerTaskTest {

    public static void main(String[] args) {
        // 创建单线程线程池
        ExecutorService threadExecutor = Executors.newFixedThreadPool(5);
        // 测试执行时长
        long num = 1000000033L;
        PrimerTask primerTask = new PrimerTask(num);
        long begin = System.currentTimeMillis();
        Boolean result = null;
        try {
            result = primerTask.call();
        } catch (Exception e) {
            // ignore
        }
        long end = System.currentTimeMillis();
        // 任务执行耗时[2043]ms
        System.err.printf("任务执行耗时[%d ms],[%d]是否为素数? [%s]\n\n", end - begin,num,result);

        // 取消任务实现
        begin = System.currentTimeMillis();
        Future<Boolean> future = threadExecutor.submit(primerTask);
        // 1s后取消任务
        boolean cancelResult = cancelTask(future, 4000);
        Boolean taskResult = null;
        try {
            taskResult = future.get();
        } catch (CancellationException e) {
            System.out.println("任务取消成功");
        } catch (InterruptedException e) {
            System.out.println("任务中断");
        } catch (ExecutionException e) {
            System.out.println("任务执行出错");
        }
        end = System.currentTimeMillis();
        System.out.printf("取消任务结果[%s],[%d]是否为素数? [%s],执行耗时[%d]",
                cancelResult,
                num,
                taskResult,
                end - begin);

        // 关闭线程池
        try {
            threadExecutor.shutdownNow();
            threadExecutor.awaitTermination(30, TimeUnit.MINUTES);
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
                System.out.printf("[%s]线程任务取消成功\n", Thread.currentThread().getName());
                innerResult = true;
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            return innerResult;
        };
        FutureTask<Boolean> futureTask = new FutureTask<>(callable);
        new Thread(futureTask, "随便定义的线程").start();
        boolean result = false;
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            // ignore
        }
        return result;
    }

}

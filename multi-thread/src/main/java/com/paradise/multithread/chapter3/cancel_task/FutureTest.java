package com.paradise.multithread.chapter3.cancel_task;

import java.util.concurrent.*;

/**
 * @author : PARADISE
 * @ClassName : FutureTest
 * @description : 测试Future接口的cancel()方法{@link Future#cancel(boolean)},中断任务执行
 * @since : 2023/3/19 10:48
 */
public class FutureTest {

    public static void main(String[] args) {
        //创建线程池
        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        // 创建任务对象
        // task 运行耗时3s
        SimpleTask simpleTask = new SimpleTask(3_000);

        // 1.第一次提交任务
        Future<Double> future1 = threadExecutor.submit(simpleTask);
        // 1.1使用cancel(false)取消任务
        boolean cancelSubmitWithFalse = future1.cancel(false);
        System.out.printf("使用[Future.cancel(false)]取消[submit]任务,结果[%s],耗时[%s]\n\n",
                cancelSubmitWithFalse,
                costTime(future1));

        // 2.第二次提交任务
        Future<Double> future2 = threadExecutor.submit(simpleTask);
        // 2.1使用cancel(true)取消任务
        boolean cancelSubmitWithTure = future2.cancel(true);
        System.out.printf("使用[Future.cancel(true)]取消[submit]任务,结果[%s],耗时[%s]\n\n",
                cancelSubmitWithTure,
                costTime(future2));

        // 3.第三次end任务
        Future<Double> future3 = threadExecutor.submit(simpleTask);
        // 3.1使用cancel(false)
        Double costTime = costTime(future3);
        boolean cancelWithResult = future3.cancel(true);
        System.out.printf("使用[Future.cancel(true)]取消[end]任务,结果[%s],耗时[%s]\n\n",
                cancelWithResult,
                costTime);

        // 4.第四次running任务
        Future<Double> future4 = threadExecutor.submit(simpleTask);
        // 4.1使用cancel(false)
        boolean cancelRunningTask = cancelTask(future4, 2_000);
        System.out.printf("使用[Future.cancel(true)]取消[running]任务,结果[%s],耗时[%s]\n\n",
                cancelRunningTask,
                costTime(future4));

        // 关闭线程池
        try {
            threadExecutor.shutdown();
            threadExecutor.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    /**
     * 计算任务耗时
     *
     * @param future future
     * @return costTime
     */
    private static Double costTime(Future<Double> future) {
        Double result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            // ignore
            System.err.print("任务执行中断\n");
        } catch (ExecutionException e) {
            // ignore
            System.err.print("任务执行出错\n");
        } catch (CancellationException e) {
            System.err.print("任务已被取消\n");
        }
        return result;
    }

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
        new Thread(futureTask,"随便定义的线程").start();
        boolean result = false;
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            // ignore
        }
        return result;
    }

}

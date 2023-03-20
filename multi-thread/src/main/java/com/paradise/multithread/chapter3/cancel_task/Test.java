package com.paradise.multithread.chapter3.cancel_task;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        // 创建一个线程池
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        // 提交一个长时间运行的任务
        Future future = executor.submit(new LongRunningTask());
        // 创建一个取消任务
        Runnable cancelTask = () -> future.cancel(true);
        // 在3秒后执行取消任务
        executor.schedule(cancelTask, 3000, TimeUnit.MILLISECONDS);
        // 关闭线程池
        executor.shutdown();
    }
}

class LongRunningTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        // 模拟长时间运行的任务
        Thread.sleep(10000);
        return "Hello";
    }
}
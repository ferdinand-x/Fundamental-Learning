package com.paradise.multithread.chapter3.cancel_task;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author : PARADISE
 * @ClassName : SimpleTask
 * @description : 定义超时任务
 * @since : 2023/3/19 10:44
 */
public class SimpleTask implements Callable<Double> {

    private final int sleepTime;

    public SimpleTask(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public Double call() throws Exception {
        long begin = System.nanoTime();
        // 线程休眠
        Thread.sleep(sleepTime);
        long end = System.nanoTime();
        // 返回任务时长(单位:秒)
        return (end - begin) / 1E9;
    }
}

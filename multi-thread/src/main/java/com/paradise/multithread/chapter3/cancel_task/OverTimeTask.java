package com.paradise.multithread.chapter3.cancel_task;

import java.util.concurrent.Callable;

/**
 * @author : PARADISE
 * @ClassName : OverTimeTask
 * @description : 定义超时任务
 * @since : 2023/3/19 15:24
 */
public class OverTimeTask implements Callable<Integer> {

    private int sleepTime;

    public OverTimeTask(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public Integer call() {
        try {
            System.out.printf("时长[%d]的任务正在被执行\n",sleepTime);
            Thread.sleep(sleepTime);
            System.out.printf("时长[%d]的任务执行完成\n",sleepTime);
        } catch (InterruptedException e) {
            System.out.printf("时长[%d]的任务取消成功\n",sleepTime);
        }
        return this.sleepTime;
    }
}

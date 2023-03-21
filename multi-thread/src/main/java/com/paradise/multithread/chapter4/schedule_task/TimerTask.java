package com.paradise.multithread.chapter4.schedule_task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : PARADISE
 * @ClassName : TimerTask
 * @description : 定时任务
 * @since : 2023/3/19 20:02
 */
public class TimerTask implements Runnable {

    private final int sleepTime;

    public TimerTask(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        // 任务开始
        System.out.printf("任务[开始],当前时间[%s]\n", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        // 任务开始
        try {
            // 模拟任务运行
            System.out.println("模拟任务运行...");
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
        // 任务结束
        System.out.printf("任务[结束],当前时间[%s]\n", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
    }
}

package com.paradise.multithread.chapter4.schedule_task;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * @author : PARADISE
 * @ClassName : TimerTaskTest
 * @description : 定时任务线程池{@link ScheduledExecutorService} 测试 {@link TimerTask}
 * @since : 2023/3/19 20:08
 */
public class TimerTaskTest {

    public static void main(String[] args) {
        // 创建线程池
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        // 定义一个执行时长 2000ms的任务
        TimerTask timerTask = new TimerTask(1000);
        System.out.printf("起始时间:%s\n\n", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        // 延迟1s后, 每3s执行一次任务
        // 使用 scheduleAtFixedRate(任务固定周期3s,即从上一个任务begin到下一个任务begin,耗时固定,为period参数)
        scheduledExecutor.scheduleAtFixedRate(timerTask, 1, 3, TimeUnit.SECONDS);
        // 使用scheduleWithFixedDelay(任务固定间隔,即上一个任务end,到下一个任务begin,耗时固定,为delay参数)
        scheduledExecutor.scheduleWithFixedDelay(timerTask,1,3,TimeUnit.SECONDS);
    }
}

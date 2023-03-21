package com.paradise.multithread.chapter3.cancel_task;

import java.util.concurrent.Callable;

/**
 * @author : PARADISE
 * @ClassName : PrimerTask
 * @description : 测试一个数字 是否为素数 此处测试{@code 1000000033 它是一个素数}
 * @since : 2023/3/19 10:36
 */
public class PrimerTask implements Callable<Boolean> {

    private long num;

    public PrimerTask(long num) {
        this.num = num;
    }

    @Override
    public Boolean call() throws Exception {
        for (long i = 2; i < num; i++) {
            // 存疑,这个判断加不加并不影响任务执行时长, 一定能成功取消任务
            if (Thread.currentThread().isInterrupted()) {
                System.out.printf("[%s]线程任务被取消\n",Thread.currentThread().getName());
                return false;
            }
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}

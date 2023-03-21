package com.paradise.multithread.chapter1.return_result;

import java.util.concurrent.Callable;

/**
 * @author : PARADISE
 * @ClassName : AccumCallable
 * @description : 通过实现Callable接口获取线程任务返回值
 * @since : 2023/3/18 22:06
 */
public class AccumCallable implements Callable<Integer> {

    private int begin;

    private int end;

    public AccumCallable(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;
        try {
            for (int i = begin; i <= end; i++) {
                result += i;
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("%s-运行结束,结果为[%d]\n",
                Thread.currentThread().getName(),
                result);
        return result;
    }
}

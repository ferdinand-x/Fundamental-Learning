package com.paradise.multithread.chapter1.return_result;

/**
 * @author : PARADISE
 * @ClassName : AccumRunnable
 * @description : 累计计算任务
 * @since : 2023/3/18 20:55
 */
public class AccumRunnable implements Runnable{

    private int begin;

    private int end;

    private int result;

    public AccumRunnable(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public int getResult() {
        return result;
    }

    @Override
    public void run() {
        result = 0;
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
    }
}

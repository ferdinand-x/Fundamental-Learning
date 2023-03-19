package com.paradise.multithread.chapter5.fork_join;

import java.util.concurrent.RecursiveTask;

/**
 * @author : PARADISE
 * @ClassName : PiEstimateTask
 * @description : 计算π值任务类 {@code  π = 4 * {1- 1/3 + 1/5 - 1/7 + 1/9 -...}}
 * @since : 2023/3/19 20:42
 */
public class PiEstimateTask extends RecursiveTask<Double> {

    private final long begin;
    private final long end;
    /**
     * 临界值
     */
    private final long threshold;

    public PiEstimateTask(long begin, long end, long threshold) {
        this.begin = begin;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected Double compute() {
        // 小于临界值,将不再分割
        if (end - begin <= threshold) {
            // 符号,多项式中奇数位取1,偶数位取-1
            int sign;
            double result = 0.0;
            for (long i = begin; i < end; i++) {
                sign = (i & 1) == 0 ? 1 : -1;
                result += sign / (i * 2.0 + 1);
            }
            return 4 * result;
        }
        // 任务分割逻辑
        long middle = (begin + end) / 2;
        RecursiveTask<Double> leftTask = new PiEstimateTask(begin, middle, threshold);
        RecursiveTask<Double> rightTask = new PiEstimateTask(middle, end, threshold);
        // 异步执行任务
        leftTask.fork();
        rightTask.fork();
        // 阻塞计算结果
        Double leftResult = leftTask.join();
        Double rightResult = rightTask.join();
        return leftResult + rightResult;
    }
}

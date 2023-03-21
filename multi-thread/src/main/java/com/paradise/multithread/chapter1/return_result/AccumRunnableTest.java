package com.paradise.multithread.chapter1.return_result;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : PARADISE
 * @ClassName : AccumRunnableTest
 * @description : 测试通过实现Runnable {@link AccumRunnable}获取返回值
 * @since : 2023/3/18 21:01
 */
public class AccumRunnableTest {

    public static void main(String[] args) {
        // 创建10个线程,每个线程分别负责累加1~10,11~20,21~30..91~100
        List<Map<AccumRunnable, Thread>> taskThreads = IntStream.range(0, 10)
                .boxed()
                .map(AccumRunnableTest::runnableTask)
                .collect(Collectors.toList());
        // 遍历线程获取总结果
        Integer accumResult = taskThreads.stream()
                .map(AccumRunnableTest::runnableResult)
                .reduce(Integer::sum).orElse(0);
        // 应得总数
        System.err.printf("应得结果[%d]\n",
                IntStream.rangeClosed(0, 100).reduce(Integer::sum).orElse(0));
        System.out.printf("累计最终结果[%d]\n", accumResult);
    }

    private static Integer runnableResult(Map<AccumRunnable, Thread> threadMap) {
        AccumRunnable accumRunnable = firstKey(threadMap);
        Thread thread = firstValue(threadMap);
        try {
            thread.join();
        } catch (InterruptedException e) {
            // ignore
            System.err.printf("线程执行异常:%s\n", e);
        }
        return accumRunnable.getResult();
    }

    private static Map<AccumRunnable, Thread> runnableTask(Integer range) {
        AccumRunnable accumRunnable = new AccumRunnable(range * 10 + 1, (range + 1) * 10);
        Thread worker = new Thread(accumRunnable, String.format("累加线程[%d]", range));
        worker.start();
        return Map.of(accumRunnable, worker);
    }

    @SuppressWarnings("unchecked")
    private static <K, V> V firstValue(Map<K, V> map) {
        return (V) map.values().toArray()[0];
    }

    @SuppressWarnings("unchecked")
    private static <K, V> K firstKey(Map<K, V> map) {
        return (K) map.keySet().toArray()[0];
    }
}

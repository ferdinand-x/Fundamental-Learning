package com.paradise.multithread.chapter7.thread_local;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : PARADISE
 * @ClassName : DateFormatWrapperTest
 * @description : 测试SimpleDateFormat {@link DateFormatWrapper}
 * 解决方案:
 * 1️⃣ 为 {@link DateFormatWrapper#format(Date)}和{@link DateFormatWrapper#parse(String)}方法 加上 {@code synchronized 关键字}
 * 2️⃣ 使用ThreadLocal {@link DateFormatThreadLocal}
 * @since : 2023/3/20 21:29
 */
public class DateFormatWrapperTest {

    public static void main(String[] args) {
        // 创建线程池(无限大小)
        ExecutorService threadPool = Executors.newCachedThreadPool();
        // 创建多个任务
        List<Future<Void>> futures = IntStream.range(0, 9)
                .boxed()
                .map(v -> threadPool.submit(new DateFormatWrapperTask()))
                .collect(Collectors.toList());
        // 获取结果集
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("日期解析出错..");
                e.printStackTrace(System.err);
            }
        }
        // 关闭线程池
        try {
            threadPool.shutdown();
            threadPool.awaitTermination(30,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    static final class DateFormatWrapperTask implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            Date date = DateFormatWrapper.parse("2023-03-20 21:35:00");
            String format = DateFormatWrapper.format(date);
            System.out.printf("[%s] -> [%s]\n", Thread.currentThread().getName(), format);
            return null;
        }
    }
}

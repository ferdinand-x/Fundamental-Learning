package com.paradise.multithread.chapter6.volatile_;

/**
 * @author : PARADISE
 * @ClassName : ConcurrentTest
 * @description : 测试volatile关键字
 * @since : 2023/3/19 21:21
 */
public class ConcurrentTest {

    /**
     * 关于volatile关键字:
     * 1.保证内存 [可见性] {@link ConcurrentTest#running}
     * 对于volatile修饰的变量,JVM可以保证:
     * 1.1每次线程对变量的写操作,都立即同步到主存
     * 1.2每次对变量的读操作,都直接从主存读取,而不是从线程栈
     * <p>
     * 2.防止指令 [重排序] {@link Singleton3#instance}
     * 3.保证对 [64位变量] 读写的原子性
     * 使用 volatile 修饰的 long 或 double 变量，JVM 可以保证对其读写的[原子性]
     * 多线程条件下使用 volatile 关键字的前提是：[对变量的写操作不依赖于变量的当前值,eg:赋值操作]
     */
    public volatile static boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        // 启动线程
        new AnotherThread().start();
        Thread.sleep(1000);
        // 修改状态
        running = false;
    }

    static final class AnotherThread extends Thread {

        @Override
        public void run() {
            System.out.println("Another Thread is running...");
            while (running) {

            }
            System.out.println("Another Thread is stopped...");
        }
    }
}

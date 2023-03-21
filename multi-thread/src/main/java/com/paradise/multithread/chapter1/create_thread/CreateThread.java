package com.paradise.multithread.chapter1.create_thread;

/**
 * @ClassName : CreateThread
 * @description : 最基本创建多线程方式
 * @author : PARADISE
 * @since : 2023/3/18 18:37
 */
public class CreateThread {

    /**
     * 通过实现Runnable方式创建多线程
     * 可以简写为lambda表达式 <code>()->{run方法逻辑}</code>
     */
    private static final Thread THREAD1 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.printf("通过实现Runnable接口创建线程:[%s]\n",
                    Thread.currentThread().getName());
            System.out.printf("[%s]线程启动...\n",
                    Thread.currentThread().getName());
            System.out.printf("[%s]线程结束...\n\n",
                    Thread.currentThread().getName());
        }
    },"Runnable线程");

    /**
     * 通过集成Thread方式创建多线程 {@link ExtThread}
     */
    private static final Thread THREAD2 = new ExtThread("Thread线程");

    public static void main(String[] args) {
        // 启动线程
        // 需要注意的是: 需要通过start方法启动线程,run方法单纯只是执行逻辑,并没有创建新的线程

        // 测试run()
        THREAD1.run();
        THREAD2.run();


        // 测试start()
        THREAD1.start();
        THREAD2.start();
    }


}

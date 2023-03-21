package com.paradise.multithread.chapter1.create_thread;

/**
 * @author PARADISE
 */
public class ExtThread extends Thread {
        private String threadName;

        public ExtThread(String threadName) {
            super(threadName);
            this.threadName = threadName;
        }
        
        @Override
        public void run(){
            System.out.printf("通过继承Thread类创建线程:[%s]\n",
                    Thread.currentThread().getName());
            System.out.printf("[%s]线程启动...\n",
                    Thread.currentThread().getName());
            System.out.printf("[%s]线程结束...\n\n",
                    Thread.currentThread().getName());
        }
    }
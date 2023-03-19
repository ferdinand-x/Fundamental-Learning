package com.paradise.multithread.chapter6.volatile_;

/**
 * @author : PARADISE
 * @ClassName : Singleton
 * @description : 单例模式实现-双检锁
 * 问题:
 * 1.分配一个 Singleton 对应的内存
 * 2.初始化这个 Singleton 对应的内存
 * 3.将 instance 指向对应的内存的地址
 * JVM 将这三条语句重排序为 1->3->2
 * @since : 2023/3/19 21:37
 */
public class Singleton2 {

    public static Singleton2 instance = null;

    private Singleton2() {
    }

    public static Singleton2 singleton() {
        if (null == instance) {
            synchronized (Singleton2.class) {
                if (null == instance) {
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }
}

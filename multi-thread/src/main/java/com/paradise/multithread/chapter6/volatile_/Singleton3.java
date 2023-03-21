package com.paradise.multithread.chapter6.volatile_;

/**
 * @author : PARADISE
 * @ClassName : Singleton
 * @description : 单例模式实现-双检锁
 * 解决 {@link Singleton2}指令重排序的问题
 * 对[instance]增加[volatile]关键字
 * @since : 2023/3/19 21:37
 */
public class Singleton3 {

    public static volatile Singleton3 instance = null;

    private Singleton3() {
    }

    public static Singleton3 singleton() {
        if (null == instance) {
            synchronized (Singleton3.class) {
                if (null == instance) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}

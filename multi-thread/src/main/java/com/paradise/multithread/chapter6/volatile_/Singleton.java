package com.paradise.multithread.chapter6.volatile_;

/**
 * @author : PARADISE
 * @ClassName : Singleton
 * @description : 单例模式实现
 * @since : 2023/3/19 21:37
 */
public class Singleton {

    public static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton singleton(){
        if (null == instance){
            instance = new Singleton();
        }
        return instance;
    }
}

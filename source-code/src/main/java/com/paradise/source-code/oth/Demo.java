package com.paradise.source_code.oth;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(()->{
            try {
                Thread.sleep(1000);
                System.out.println("1111111111111111111111111");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        tasks.add(()-> System.out.println("22222222222222222"));
        tasks.add(()-> System.out.println("33333333333333333"));

        Guardian guardian = new Guardian(500);
        guardian.start();

        TaskRunner taskRunner = new TaskRunner(tasks, guardian);
        taskRunner.start();
    }
}

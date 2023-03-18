package com.paradise.code.oth;

import java.util.List;

/**
 * @author PARADISE
 */
public class TaskRunner extends Thread {
    private List<Runnable> tasks;
    private Guardian guardian;

    public TaskRunner(List<Runnable> tasks, Guardian guardian) {
        this.tasks = tasks;
        this.guardian = guardian;
    }

    @Override
    public void run() {
        for (Runnable task : tasks) {
            synchronized (guardian) {
                guardian.startTask(task);
            }
            task.run();
            synchronized (guardian) {
                guardian.stopTask();
            }
        }
    }
}

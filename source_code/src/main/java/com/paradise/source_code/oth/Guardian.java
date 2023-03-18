package com.paradise.source_code.oth;

/**
 * @author PARADISE
 */
public class Guardian extends Thread {
    private long timeout;
    private Runnable currentTask;
    private boolean shouldStop;

    public Guardian(long timeout) {
        this.timeout = timeout;
    }

    public synchronized void startTask(Runnable task) {
        this.currentTask = task;
        this.shouldStop = false;
        this.notifyAll();
    }

    public synchronized void stopTask() {
        this.shouldStop = true;
        this.currentTask = null;
        this.notifyAll();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (currentTask == null) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Guardian interrupted");
                    }
                }
            }
            long start = System.currentTimeMillis();
            Thread currThread = Thread.currentThread();
            synchronized (currentTask) {
                if (!shouldStop) {
                    try {
                        currentTask.wait(timeout);
                    } catch (InterruptedException e) {
                        System.err.println("Guardian interrupted");
                    }
                    long end = System.currentTimeMillis();
                    if (end - start >= timeout && !shouldStop) {
                        System.err.println("Task timed out after " + timeout + " milliseconds");
                        currentTask = null;
                        currThread.start();
                    }
                }
            }
        }
    }
}

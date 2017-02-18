package com.antilia.util.thread;

/**
 * ThreadUtils
 */
public class ThreadUtils {

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
}

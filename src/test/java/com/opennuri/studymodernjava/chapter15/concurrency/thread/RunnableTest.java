package com.opennuri.studymodernjava.chapter15.concurrency.thread;

import org.junit.jupiter.api.Test;

public class RunnableTest {

    @Test
    void runnabel() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread: " + Thread.currentThread().getName());
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("Hello: " + Thread.currentThread().getName());
    }
}

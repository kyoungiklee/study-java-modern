package com.opennuri.studymodernjava.chapter15.concurrency.thread;

import org.junit.jupiter.api.Test;

public class ThreadTest {

    @Test
    void threadStart() {
        Thread thread = new MyThread();
        thread.start();
        System.out.println("Hello: " + Thread.currentThread().getName());

    }

    @Test
    void threadRun() {
        Thread thread = new MyThread();
        thread.run();
        thread.run();
        thread.run();
        System.out.println("Hello: " + Thread.currentThread().getName());
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread: " + Thread.currentThread().getName());
        }
    }
}

package com.opennuri.studymodernjava.chapter15;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {
    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        work1();
        //10 초 후 
        scheduledExecutorService.schedule(ScheduledExecutorServiceExample::work2, 10, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }

    private static void work1() {
        System.out.println("Hello frm work1");
    }

    private static void work2() {
        System.out.println("Hello form work2");
    }
}

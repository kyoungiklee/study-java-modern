package com.opennuri.studymodernjava.chapter15.concurrency.future;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTest {

    @SuppressWarnings("resource")
    @Test
    void schedule() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(runnable, 1, TimeUnit.SECONDS);

        Thread.sleep(1000L);
        scheduledExecutorService.shutdown();
    }

    @SuppressWarnings("resource")
    @Test
    void scheduleAtFixRate() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finish shceduleAtFixRate: " + LocalTime.now());
        };

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);

        Thread.sleep(1000L);
        executorService.shutdown();
    }
}

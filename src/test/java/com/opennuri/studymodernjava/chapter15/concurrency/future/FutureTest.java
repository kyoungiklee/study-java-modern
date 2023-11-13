package com.opennuri.studymodernjava.chapter15.concurrency.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

public class FutureTest {
    @Test
    void get() throws ExecutionException, InterruptedException {

        @SuppressWarnings("resource") ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(3000L);
            return "Thread: " + Thread.currentThread().getName();
        };
        Future<String> future = executorService.submit(callable);
        System.out.println(future.get());

        executorService.shutdown();
    }

    @Test
    void isCancelled_flase() {

        @SuppressWarnings("resource") ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(3000L);
            return "Thread: " + Thread.currentThread().getName();
        };

        Future<String> future = executorService.submit(callable);
        assertThat(future.isCancelled()).isFalse();
        executorService.shutdown();

    }

    @Test
    void isCancelled_ture()  {
        @SuppressWarnings("resource") ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(3000L);
            return "Thread: " + Thread.currentThread().getName();
        };

        Future<String> future = executorService.submit(callable);
        future.cancel(true);

        assertThat(future.isCancelled()).isTrue();
        executorService.shutdown();

    }

    @Test
    void isDone_false() {
        @SuppressWarnings("resource") ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(3000L);
            return "Thread: " + Thread.currentThread().getName();
        };

        Future<String> future = executorService.submit(callable);
        assertThat(future.isDone()).isFalse();
        executorService.shutdown();

    }

    @Test
    void isDone_true() {
        @SuppressWarnings("resource") ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(3000L);
            return "Thread: " + Thread.currentThread().getName();
        };

        Future<String> future = executorService.submit(callable);

        while (future.isDone()) {
            assertThat(future.isDone()).isTrue();
        }
        executorService.shutdown();
    }
}

package com.opennuri.studymodernjava.chapter15.concurrency.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CallableTest {

    @Test
    void callableNotReturn() {
        @SuppressWarnings("resource") ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<Void> callable =  new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                String result = "Thread: " + Thread.currentThread().getName();
                System.out.println(result);
                return null;
            }
        };

        executorService.submit(callable);
        executorService.shutdown();
    }

    @Test
    void callableReturnString() throws ExecutionException, InterruptedException {
        @SuppressWarnings("resource") ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Thread: " + Thread.currentThread().getName();
            }
        };

        Future<String> submit = executorService.submit(callable);
        System.out.println(submit.get());
        executorService.shutdown();
    }
}

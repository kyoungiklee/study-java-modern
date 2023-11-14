package com.opennuri.studymodernjava.chapter15.concurrency.future;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExecutorServiceTest {
    @Test
    void shutdown() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread: " + Thread.currentThread().getName());
            }
        };
        executorService.execute(runnable);
        executorService.shutdown();

        assertThatThrownBy(() -> executorService.execute(runnable)).isInstanceOf(RejectedExecutionException.class);
    }
    @Test
    void invokeAll() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Instant start = Instant.now();

        Callable<String> hello = () -> {
            Thread.sleep(1000L);
            String result = "Hello";
            System.out.println("result: " + result);
            return result;
        };

        Callable<String> world = () -> {
            Thread.sleep(2000L);
            String result = "World!";
            System.out.println("result: " + result);
            return result;
        };

        Callable<String> thread = () -> {
            Thread.sleep(3000L);
            String result = "Thread";
            System.out.println("result: " + result);
            return result;
        };

        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, world, thread));
        for(Future<String> future : futures) {
            System.out.println(future.get());
        }

        System.out.println("time= " + Duration.between(start, Instant.now()).getSeconds());
        executorService.shutdown();
    }

    @Test
    void invokeAny() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Instant start = Instant.now();

        Callable<String> hello = () -> {
            Thread.sleep(1000L);
            String result = "Hello";
            System.out.println("result: " + result);
            return result;
        };

        Callable<String> world = () -> {
            Thread.sleep(2000L);
            String result = "World!";
            System.out.println("result: " + result);
            return result;
        };

        Callable<String> thread = () -> {
            Thread.sleep(3000L);
            String result = "Thread";
            System.out.println("result: " + result);
            return result;
        };

        String result = executorService.invokeAny(Arrays.asList(hello, world, thread ));
        System.out.println("result: " + result + " time: " + Duration.between(start, Instant.now()).getSeconds());
        executorService.shutdown();

    }
}

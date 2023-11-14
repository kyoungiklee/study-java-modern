package com.opennuri.studymodernjava.chapter15.concurrency.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

public class ExecutorTest {

    @Test
    void executorRun() {

        Runnable runnable = () -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        };

        Executor executor = new RunExecutor();
        executor.execute(runnable);
    }
    static class RunExecutor implements Executor {
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    @Test
    void executorStart() {
        Runnable runnable = () -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        };

        Executor executor = command -> {
            new Thread(command).start();
        };

        executor.execute(runnable);

    }
}

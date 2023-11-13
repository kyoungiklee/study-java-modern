package com.opennuri.studymodernjava.chapter15.concurrency.future;

import java.util.concurrent.*;

public class SquareCalculator {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Future<Integer> calculate(Integer input) {

        //Callable is an interface representing a task that returns a result, and has a single call() method
        return executorService.submit(() -> {
            Thread.sleep(1000L);
            return input * input;
        });
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        SquareCalculator squareCalculator=  new SquareCalculator();
        Future<Integer> future = squareCalculator.calculate(10);

        //Future.isDone() tells us if the executor has finished processing the task. If the task is complete,
        // it will return true; otherwise, it returns false.
        while(!future.isDone()) {
            System.out.println("Calculating....");
            Thread.sleep(300L);
        }
        //The method that returns the actual result from the calculation is Future.get().
        // We can see that this method blocks the execution until the task is complete.
        Integer result = future.get(500L, TimeUnit.MILLISECONDS);
        System.out.println("Result: " + result);

        squareCalculator.executorService.shutdown();
    }
}

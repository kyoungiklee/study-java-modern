package com.opennuri.studymodernjava.chapter15;

import java.util.concurrent.*;
import java.util.function.BiFunction;

import static com.opennuri.studymodernjava.chapter15.Functions.f;
import static com.opennuri.studymodernjava.chapter15.Functions.g;

public class CFCombine {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        CompletableFuture<Integer> b = new CompletableFuture<>();

        //첫번째 CompletableFuture의 둉작 완료에 관계없이 두번째 CompletableFuture를 실행하는 경우
        //독립적으로 실행된 두개의 CompletableFuture 결과를 합쳐야할 때 사용
        //이 경우 thenCombine 메서드를 사용할 수 있다. thenCombine 메서드는 BiFunction을 두번째 인수로 받는데
        //두개의 CompletableFuture 결과를 어떻게 합칠지를 정의하면 된다.
        CompletableFuture<Integer> c = a.thenCombine(b, new BiFunction<Integer, Integer, Integer>() {
            // 두개의 CompletableFuture를 어떻게 합칠지 정의하는 펑션
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2; //4012
            }
        });

        //CompletableFuture의 complete() 를 호출하여 쓰레드의 종료 여부를 확인할 수 있다.
        executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return a.complete(f(x)); // x + 1 = 1338
            }
        });
        executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return b.complete(g(x)); //x * 2 = 2674
            }
        });

        try {
            //독립적으로 실행된 두개의 Thread 의 처리가 종료되면 CompletableFuture가 만들어지고 thenCombine() 을 사용하여 두개의
            //CompletableFuture 값을 조합할 수 있다.
            System.out.println(c.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();

    }
}

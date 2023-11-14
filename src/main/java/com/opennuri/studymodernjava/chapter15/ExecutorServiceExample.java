package com.opennuri.studymodernjava.chapter15;

import java.util.concurrent.*;

import static com.opennuri.studymodernjava.chapter15.Functions.fo;
import static com.opennuri.studymodernjava.chapter15.Functions.go;

public class ExecutorServiceExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int x = 1337;

        //Thread pool을 관리하는 서비스
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> y = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fo(x); //x * 2 = 2674
            }
        });

        //ExecutorService의 submit() 을 이용하여 ExecutorService로 제출한다.
        Future<Integer> z = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return go(x);  //x + 1 = 1338
            }
        });

        //ExecutorService에서 실행이 종료 되면 처리 결과를 받아 온다.
        System.out.println(y.get() + z.get());
        executorService.shutdown();

    }
}

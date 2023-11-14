package com.opennuri.studymodernjava.chapter15.concurrency.completable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Slf4j
public class CompletableFutureTest {
    @Test
    void calculateAsyncTest() {

        Future<String> complitableFuture = calculateAsync();

    }
    public Future<String> calculateAsync() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500L);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

    @SuppressWarnings("resource")
    @Test
    void use_future_asynchronous_calculation_modeling() {
        //Java5 부터는 미래의 어느 시점에 결과를 얻는 모델에 활용할 수 있도록 Future 인터페이스를 제공하고 있다.
        //비동기 계산을 모델링하는 데 Future를 이용할 수 있으며, Future는 계산이 끝났을 때 결과에 접근할 수 있는 참조를 제공한다.

        ExecutorService executorService = Executors.newCachedThreadPool();

        Callable<String> callable = () -> {
            System.out.println("Future call start: ");
            Thread.sleep(5000); // 5초 작업시간이 필요한 작업 모델링
            System.out.println("Future call complete: ");
            return "Future Call: ";
        };

        //Future를 이용하려면 시간이 오래 걸리는 작업을 Callable 객체 내부로 감싼 다음에 ExecutorService에 제출해야 한다.
        Future<String> future = executorService.submit(callable);

        //시간이 걸릴 수 있는 작업을 Future 내부로 설정하면 호출자 스레드가 결과를 기다리는 동안 다른 유용한 작업을 수행할 수 있다.
        try {
            Thread.sleep(3000L); // 3초 작업시간이 필요한 작업 모델링
            System.out.println("Do something else: ");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //get 메서드를 호출했을 때 이미 계산이 완료되었다면 즉시 결과를 반환하지만 결과가 준비되지 않았다면 작업이 완료될 때까지 스레드를 블록 시킨다.
        //오래 걸리는 작업이 영원히 끝나지 않는 것을 방지하기 위해 get 메서드를 overload 해서 스레드가 대기할 최대 타임아웃 시간을 설정하는 것이 좋다.
        try {
            String result = future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class Food {
        private String name;
    }

    public static Future<Food> cookingAsync(String foodName, long start) {

        CompletableFuture<Food> futureFood = new CompletableFuture<>();

        new Thread(() -> {
            try {
                Thread.sleep(2000L); // 작업 시간 모델링
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Food food = new Food(foodName);
                futureFood.complete(food);
            }catch (Exception e) {
                //completeExcpetionally 메서드를 이용해서 CompletableFuture 내부에서 발생한 예외를 클라이언트로 전달해야 한다.
                futureFood.completeExceptionally(e);
            }

            long cookingAsyncTime = ((System.nanoTime() - start) / 1_000_000);
            System.out.println(foodName + " 요리중....(" + cookingAsyncTime + "msecs)");
        }).start();

        return futureFood;
    }

    @Test
    void cook_two_dishes_at_once() {
        long start = System.nanoTime();
        // 실행 결과를 보면 요리가 끝나기 전에 Future가 반환되는 것을 확인할 수 있다.
        Future<Food> pizzaFuture = cookingAsync("Pizza", start);
        System.out.println("pizza future 반환: ");
        Future<Food> pastaFuture = cookingAsync("Pasta", start);
        System.out.println("pasta future 반환: ");

        // 피자와 파스타를 모두 요리하는데 약 4초가 걸리지만 동시에 요리했으므로 2초 만에 두 요리가 모두 완성되었다.
        try {
            Food pizza = pizzaFuture.get();
            long pizzaCompletedTime = ((System.nanoTime() - start) / 1_000_000);
            System.out.println(pizza.getName() + " 완성! (" + pizzaCompletedTime + " msecs)");

            Food pasta = pastaFuture.get();
            long pastaCompletedTime = ((System.nanoTime() - start) / 1_000_000);
            System.out.println(pizza.getName() + " 완성! (" + pizzaCompletedTime + " msecs)");

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public static Future<Food> cookingSupplyAsync(String foodName, long start) {
        //supplyAsync 메서드는 Supplier를 인수로 받아서 비동기 실행 후 CompletableFuture를 반환한다.

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L); //작업 시간을 모델링
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            long cookingAsyncTime = ((System.nanoTime() - start) / 1_000_000);
            System.out.println(foodName + " 요리중....(" + cookingAsyncTime + "msecs)");

            return new Food(foodName);
        });
    }

    @Test
    void use_parallel_stream() {
        List<String> foodNames = List.of("Pizza", "Pasta", "Hamburger");

        //최대로 활용할 수 있는 스레드의 개수가 4개이고 병렬 처리할 스레드가 5개일 때,
        // 4개의 스레드가 끝난 후 다음 스레드가 실행되기 때문에 2배의 시간이 더 걸릴 수 있다.
        List<Food> foods = foodNames.parallelStream().map(foodName -> {
            long start = System.nanoTime();
            try {
                Thread.sleep(2000L); // 작업 시간 모델링
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long cookingAsyncTime = ((System.nanoTime() - start) / 1_000_000);
            System.out.println(foodName + " 요리중....(" + cookingAsyncTime + "msecs)");

            return new Food(foodName);
        }).toList();

        log.debug("Parallel Stream: " + foods);

    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void use_future_join_stream() {
        List<String> foodNames = List.of("Pizza", "Pasta", "Hamburger");
        log.debug("Math.min() 값 확인: {}", Math.min(foodNames.size(), 100));

        //CompletableFuture 버전이 병렬 스트림 버전보다 아주 조금 빠르다.
        //결과적으로는 비슷하지만 CompletableFuture는 병렬 스트림 버전이 비해 작업에 이용할 수 있는 다양한 Executor를 지정할 수 있어,
        //Executor로 스레드 풀의 크기를 조절하는 등 애플리케이션에 맞는 최적화된 설정을 만들 수 있다.

        //자바에서 일반 스레드가 실행 중이면 자바 프로그램은 종료되지 않는다.
        // 따라서 어떤 이벤트를 한없이 기다리면서 종료되지 않는 일반 스레드가 있으면 문제가 될 수 있기 때문에
        // 데몬 스레드를 사용해 자바 프로그램이 종료될 때 강제로 종료되도록 만든다.
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(foodNames.size(), 12), new ThreadFactory() {
            @SuppressWarnings("NullableProblems")
            @Override

            public Thread newThread(Runnable runnable) {
                Thread t = new Thread(runnable);
                t.setDaemon(true); // 프로그램 종료를 방해하지 않는 데몬 스레드 사용
                return t;
            }
        });

        //비동기 동작을 많이 사용하는 상황에서는 이렇게 커스텀 Executor를 만들어 CompletableFuture를 활용하는 것이 가장 효과적일 수 있다.
        List<CompletableFuture<Food>> foodFutures = foodNames.stream().map(
                foodName -> CompletableFuture.supplyAsync(() -> {
                    long start = System.nanoTime();
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    long cookingAsyncTime = ((System.nanoTime() - start) / 1_000_000);
                    System.out.println(foodName + " 요리중....(" + cookingAsyncTime + "msecs)");
                    return new Food(foodName);
                }, executor)
        ).toList();

        List<Food> foods = foodFutures.stream()
                .map(CompletableFuture::join)
                .toList();
        log.debug("CompletableFuture supplyAsync: " + foods);
    }

}

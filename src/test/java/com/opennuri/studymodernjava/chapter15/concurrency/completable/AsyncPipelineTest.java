package com.opennuri.studymodernjava.chapter15.concurrency.completable;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
public class AsyncPipelineTest {

    @Getter
    @AllArgsConstructor
    @ToString
    public static class Food {
        private String name;
    }


    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Dish {
        private Food food;
    }
    @SuppressWarnings("DataFlowIssue")
    @Test
    @DisplayName(value = "동기 작업과 비동기 작업 조합하기")
    void combining_Synchronous_and_Asynchronous_Jobs() {
        List<String> foodNames = List.of("Pizza", "Hamburger", "Pasta", "Gimbap", "Topokki");

        ExecutorService executor = Executors.newFixedThreadPool(Math.min(foodNames.size(), 100), runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
        //supplyAsync 메서드를 이용해 비동기적으로 음식을 요리하고
        //CompletableFuture에 thenApply 메서드를 호출해 어떤 음식이 완성되었는지 확인 후 다시 음식을 전달한다.
        List<CompletableFuture<Dish>> dishesFuture = foodNames.stream()
                .map(foodName -> CompletableFuture.supplyAsync(() -> {
                    System.out.println(foodName + " 요리중....");
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new Food(foodName);
                }, executor))
                .map(future -> future.thenApply(food -> {
                    System.out.println(food.getName() + " 완성");
                    return food;
                }))
                .map(future -> future.thenCompose(food -> CompletableFuture.supplyAsync(() -> {
                    System.out.println(food.getName() + " 접시에 담기");
                    return new Dish(food);
                }, executor)))
                .toList();

        List<Dish> dishes = dishesFuture.stream().map(CompletableFuture::join)
                .toList();
        log.debug("음식 준비 완료: " + dishes);

    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    @DisplayName(value = "독립된 두 개의 CompletableFuture 합치기")
    void combine_two_independent_completeFuture() {
        //때로는 두 개의 CompletableFuture의 비동기 계산이 끝난 후 두 결과물을 합쳐야 할 경우가 있다.
        //물론 첫 번째 CompletableFuture의 동작 완료와 상관없이 두 번째 CompletableFuture를 실행할 수 있어야 한다.
        //이런 상황에서는 thenCombine 메서드를 사용할 수 있다.
        List<String> foodNames = List.of("Pizza", "Hamburger", "Pasta", "Gimbap", "Topokki");

        ExecutorService executor = Executors.newFixedThreadPool(Math.min(foodNames.size(), 100), runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        List<CompletableFuture<Dish>> dishesFuture = foodNames.stream()
                .map(foodName -> CompletableFuture.supplyAsync(() -> {
                    System.out.println(foodName + " 요리중...");
                    return new Food(foodName);
                    //orTimeout 메서드는 지정된 시간이 지난 후에 CompletableFuture를 TimeoutException으로 완료하면서
                    // 또 다른 CompletableFuture를 반환할 수 있도록 내부적으로 ScheduledThreadExecutor를 활용한다.
                }, executor).orTimeout(3, TimeUnit.SECONDS))
                .map(future -> future.thenApply(food -> {
                    System.out.println(food.getName() + " 완성");
                    return food;
                }))
                .map(future -> future.thenCombine(CompletableFuture.supplyAsync(Dish::new, executor), (food, dish) -> {
                    dish.setFood(food);
                    System.out.println(food.getName() + " 접시에 담기");
                    return dish;
                    //completeOnTimeout를 이용해 기본값을 지정할 수도 있다.
                }).completeOnTimeout(new Dish(),3, TimeUnit.SECONDS)).toList();
        List<Dish> dishes = dishesFuture.stream().map(CompletableFuture::join).toList();

        log.debug("독립된 두 개의 CompletableFuture 합치기: " + dishes);
    }

    @Test
    @DisplayName(value = "CompletableFuture 종료에 대응하는 방법")
    void how_to_respond_to_completeFuture_shutdown() {

        List<String> foodNames = List.of("Pizza", "Hamburger", "Pasta", "Gimbap", "Topokki");

        ExecutorService executor = Executors.newFixedThreadPool(Math.min(foodNames.size(), 100), runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        Random random = new Random();

        Stream<CompletableFuture<Dish>> dishStream = foodNames.stream()
                .map(foodName -> CompletableFuture.supplyAsync(() -> {
                    int delay = random.nextInt(2000);
                    System.out.println(foodName + " 요리시작 (" + delay + "msecs)");
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new Food(foodName);
                }))
                .map(future -> future.thenCompose(food ->
                        CompletableFuture.supplyAsync(() -> new Dish(food), executor)));

        //thenAccept 메서드는 새로운 스레드를 이용해서 CompletableFuture의 결과를 소비한다.
        CompletableFuture[] futures = dishStream
                .map(future -> future.thenAccept(dish -> {
                    System.out.println(dish.getFood().getName() + "완성 후 접시에 담기");
                }))
                .toArray(size -> new CompletableFuture[size]);
        //팩토리 메서드 allOf를 사용해 CompletableFuture<Void>를 반환한다.
        //전달된 모든 CompletableFuture가 완료되어야 CompletableFuture<Void>가 완료된다.
        //allOf 메서드가 반환하는 CompletableFuture에 join을 호출하면 원래 스트림의 모든 CompletableFuture의 실행 완료를 기다릴 수 있다.
        CompletableFuture.allOf(futures).join();

    }
}

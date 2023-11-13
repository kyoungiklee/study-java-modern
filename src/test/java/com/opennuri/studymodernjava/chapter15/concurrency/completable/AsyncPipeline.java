package com.opennuri.studymodernjava.chapter15.concurrency.completable;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
public class AsyncPipeline {

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
                }, executor))
                .map(future -> future.thenApply(food -> {
                    System.out.println(food.getName() + " 완성");
                    return food;
                }))
                .map(future -> future.thenCombine(CompletableFuture.supplyAsync(Dish::new, executor), (food, dish) -> {
                    dish.setFood(food);
                    System.out.println(food.getName() + " 접시에 담기");
                    return dish;
                })).toList();
        List<Dish> dishes = dishesFuture.stream().map(CompletableFuture::join).toList();

        log.debug("독립된 두 개의 CompletableFuture 합치기: " + dishes);
    }
}

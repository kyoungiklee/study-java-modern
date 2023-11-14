package com.opennuri.studymodernjava.chapter16.service;

import com.opennuri.studymodernjava.chapter16.domain.Discount;
import com.opennuri.studymodernjava.chapter16.domain.Quote;
import com.opennuri.studymodernjava.chapter16.domain.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BestPriceFinder {

    private final List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"));

    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 10), (Runnable runnable) -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });


    public List<String> findPricesSequential(String product) {
        return null;
    }

    public List<String> findPricesParallel(String product) {
        return null;
    }

    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFuture = findPricesStream(product)
                .collect(Collectors.<CompletableFuture<String>>toList());

        //각각의 Thread가 완료되면 CompletableFuture에서 값을 찾는다.
        //각각의 Thread가 모두 완료 될떼까지 블로킹 된다.
        List<String> prices = priceFuture.stream()
                .map(new Function<CompletableFuture<String>, String>() {
                    @Override
                    public String apply(CompletableFuture<String> future) {
                        return future.join(); //CompletableFuture의 get()과 join()의 차이
                    }
                })
                .collect(Collectors.toList());

        return prices;
    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(new Function<Shop, CompletableFuture<String>>() {
                    @Override
                    public CompletableFuture<String> apply(Shop shop) {
                        return CompletableFuture.supplyAsync(new Supplier<String>() {
                            @Override
                            public String get() {
                                //상품에 대하여 각 상점으로부터 상점명, 가격, 할인률 문자열을 받아온다.
                                return shop.getPrice(product);
                            }
                        }, executor);
                    }
                })
                // 각 상점으로부터 받은 상점명, 가격, 할인률 문자열로 부터 견적을 생성한다.
                .map(future -> future.thenApply(Quote::parse))
                .map(new Function<CompletableFuture<Quote>, CompletableFuture<String>>() {
                    @Override
                    public CompletableFuture<String> apply(CompletableFuture<Quote> future) {
                        return future.thenCompose(new Function<Quote, CompletionStage<String>>() {
                            @Override
                            public CompletionStage<String> apply(Quote quote) {
                                return CompletableFuture.supplyAsync(new Supplier<String>() {
                                    @Override
                                    public String get() {
                                        //각 상점의 Quote로 부터 할인가를 구한다.
                                        return Discount.applyDiscount(quote);
                                    }
                                }, executor);
                            }
                        });
                    }
                });
    }

    public void printPricesStream(String product) {

    }
}

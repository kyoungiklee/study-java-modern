package com.opennuri.studymodernjava.chapter16.client;

import com.opennuri.studymodernjava.chapter16.service.BestPriceFinder;

import java.util.List;
import java.util.function.Supplier;

public class BestPriceFinderMain {
    private static BestPriceFinder bestPriceFinder = new BestPriceFinder();

    public static void main(String[] args) {
        execute("Composed CompletableFuture",() -> bestPriceFinder.findPricesFutureLambda("myPhone27s"));

    }

    private static void execute (String msg, Supplier<List<String>> supplier) {
        long start = System.nanoTime();
        System.out.println(supplier.get());
        long duration = (System.nanoTime() -start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");
    }
}

package com.opennuri.studymodernjava.chapter7;

import java.util.function.Function;

public class ParallelStreamsHarness {
    public static void main(String[] args) {
        System.out.println("Iterative Sum done in: "
                + measurePerf(ParallelStreams::iterativeSum, 10_000_000L) + "msecs");
        System.out.println("Sequantial Sum done in: "
                + measurePerf(ParallelStreams::sequentialSum, 10_000_000L) + "msecs");
    }

    private static <T, R> long measurePerf(Function<T, R> f, T input) {
        long fastest = Long.MAX_VALUE;

        for(int i =0; i < 10; i++) {
            long start = System.nanoTime();
            R result = f.apply(input);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Resutl: " + result);
            if (duration < fastest) {
                fastest = duration;
            }
        }
        return fastest;
    }
}

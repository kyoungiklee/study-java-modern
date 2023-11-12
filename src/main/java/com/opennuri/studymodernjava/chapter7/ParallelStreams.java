package com.opennuri.studymodernjava.chapter7;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0;  i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n)
                .reduce(Long::sum).orElse(0L);
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n)
                .parallel()
                .reduce(Long::sum).orElse(0L);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(Long::sum).orElse(0L);
    }

    public static long sideEffectSum(Long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    public static class Accumulator {
        private long total = 0;

        public void add(long value) {
            total += value;
        }
    }

}

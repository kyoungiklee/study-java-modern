package com.opennuri.studymodernjava.chapter7;

public class ParallelStreams {

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0;  i < n; i++) {
            result += i;
        }
        return result;
    }
}

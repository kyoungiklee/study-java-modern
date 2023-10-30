package com.opennuri.studymodernjava.chapter0;

public class GenericsMethods {
    public static <T> boolean isEquals(GenericsType<T> g1, GenericsType<T> g2) {
        return g1.get().equals(g2.get());
    }

    public static <T extends Comparable<T>> int compare(T t1, T t2) {
        return t1.compareTo(t2);
    }

    public static void main(String[] args) {
        GenericsType<String> g1 = new GenericsType<>();
        g1.set("Pankal");

        GenericsType<String> g2 = new GenericsType<>();
        g2.set("Pankal");

        boolean isEqual = GenericsMethods.<String>isEquals(g1, g2);
        //above statement can be written simply as
        isEqual = GenericsMethods.isEquals(g1, g2);
        //This feature, known as type inference,
        // allows you to invoke a generic method as an ordinary method,
        // without specifying a type between angle brackets.
    }
}

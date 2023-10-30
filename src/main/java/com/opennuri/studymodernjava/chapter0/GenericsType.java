package com.opennuri.studymodernjava.chapter0;

public class GenericsType<T> {
    private T t;

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    @SuppressWarnings("rowtypes")
    public static void main(String[] args) {
        GenericsType<String> genericsType = new GenericsType<>();
        genericsType.set("pankal");

        //Raw use of parameterized class 'GenericsType'
        GenericsType type1 = new GenericsType();
        type1.set("pankal");
        type1.set(10);
    }
}

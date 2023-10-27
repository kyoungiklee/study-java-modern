package chapter0;

import java.util.Map;

public class ExMultiTypeGeneric<K,V>{
    private K key;
    private V value;


    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        this.value = value;
        return value;
    }


    public static void main(String[] args) {
        ExMultiTypeGeneric<String, Integer> exMultiTypeGeneric = new ExMultiTypeGeneric<>();

    }
}

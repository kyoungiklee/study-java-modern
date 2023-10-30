package chapter0;

public class ClassNameTwo <K, V>{
    private K first;
    private V second;

    public void set(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}

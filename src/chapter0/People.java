package chapter0;

public class People <T, M>{
    private T name;
    private M age;

    public People(T name, M age) {
        this.name = name;
        this.age = age;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public M getAge() {
        return age;
    }

    public void setAge(M age) {
        this.age = age;
    }

    public static <T, V> boolean compare(People<T, V>p1, People<T, V> p2) {
        if(p1 == null || p2 == null) return false;
        boolean eq = p1.getName().equals(p2.getName());
        eq = eq && p1.getAge().equals(p2.getAge());
        return eq;
    }

    public static void main(String[] args) {
        People<String, Integer> kyoungik = new People<>("kyoungik", 30);
        People<String, Integer> sunik = new People<>("kyoungik", 30);

        System.out.println(People.compare(kyoungik, sunik));

    }
}

package chapter0;

public class ClassNameThree <K extends Number> {

    private K num;

    public void setNum(K num) {
        this.num = num;
    }

    public K getNum() {
        return this.num;
    }

    @Override
    public String toString() {
        return "ClassNameThree{" +
                "num=" + num +
                '}';
    }
}

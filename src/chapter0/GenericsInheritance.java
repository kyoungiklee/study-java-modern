package chapter0;

import java.util.ArrayList;

public class GenericsInheritance {
    public static void main(String[] args) {
        String str = "abc";
        Object obj = new Object();

        obj =str; //// works because String is-a Object, inheritance in java

        MyClass<String> myClass1 = new MyClass<>();
        MyClass<Object> myClass2 = new MyClass<>();
        // compilation error since MyClass<String> is not a MyClass<Object>
        //myClass2 = myClass1;
        obj = myClass2;

        ArrayList list = new ArrayList(); //제네릭을 사용하지 않을경우
        list.add("test");
        String temp = (String) list.get(0); //타입변환이 필요함

        ArrayList<String> list2 = new ArrayList<>(); //제네릭을 사용할 경우
        list2.add("test");
        temp = list2.get(0); //타입변환이 필요없음

    }

    public static class MyClass<T> {

    }

    public void test() {
        Fruit fruit = new Fruit();
        Apple apple = new Apple();
        fruit = apple;
        apple = (Apple) fruit;
    }

    public class Fruit {

    }

    public class Apple extends Fruit {

    }
}

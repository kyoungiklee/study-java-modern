package com.opennuri.studymodernjava.chapter05;



import com.opennuri.studymodernjava.chapter04.Dish;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class Mapping {

    public static final List<Dish> menu = asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 400, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );
    public static void main(String[] args) {

        //메뉴명을 출력하시오
        menu.stream().map(Dish::getName).toList().forEach(System.out::println);


        //스트링 리스트의 스트링 길이의 합을 출력하시오
        List<String> words = Arrays.asList("Hello", "World");

        Integer wordCount = words.stream()
                .map(String::length)
                .reduce(0, Integer::sum);
        System.out.println(wordCount);

        //스트링 리스트에서 고유한 문자를 출력하시오
        words.stream()
                .flatMap(s -> Arrays.stream(s.split("")))
                .distinct()
                .forEach(System.out::print);

        //주어진 숫자 리스트를 숫자 짝의 합이 3의 배수인 값을 출력하시오. 출력형태: (1, 8),......,(5,7)
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);

        numbers1.stream()
                .flatMap(i -> numbers2.stream().map(j -> new int[] {i, j}))
                .filter(i -> (i[0] + i[1]) % 3 == 0)
                .toList()
                .forEach(i -> System.out.printf("(%d, %d) ", i[0], i[1]));
    }
}

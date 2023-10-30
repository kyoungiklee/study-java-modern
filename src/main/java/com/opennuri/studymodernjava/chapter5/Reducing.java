package com.opennuri.studymodernjava.chapter5;



import com.opennuri.studymodernjava.chapter04.Dish;

import java.util.Arrays;
import java.util.List;

public class Reducing {

    public static final List<Dish> menu = Arrays.asList(
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

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        //주어진 숫자 리스트의 합계를 구하시오
        System.out.println(numbers.stream().reduce(0, Integer::sum)
                .intValue());

        System.out.println();
        System.out.println(numbers.stream()
                .reduce(0, Integer::sum).intValue());
        System.out.println();

        //주어진 숫자 리스트의 가장 큰 값을 구하시오
        System.out.println(numbers.stream()
                .reduce(0, Integer::max)
                .intValue());
        System.out.println();

        //주어진 숫자 리스트의 가장 작은 값을 구하시오
        numbers.stream()
                .reduce(Integer::min)
                .ifPresent(System.out::println);

        //메뉴 리스트의 칼로리 합계를 구하시오

        System.out.printf("Number of calories: %d",menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum));

    }
}

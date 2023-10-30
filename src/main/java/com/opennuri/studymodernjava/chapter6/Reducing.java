package com.opennuri.studymodernjava.chapter6;

import chapter04.Dish;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.reducing;

public class Reducing {

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
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithoutCollectors());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithMethodReferenceUsingReducing());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesUsingSum());
    }

    public static int calculateTotalCaloriesUsingSum() {
        return menu.stream()
                .mapToInt(Dish::getCalories).sum();
    }

    public static int calculateTotalCaloriesWithMethodReferenceUsingReducing() {
        return menu.stream()
                .collect(reducing(
                        0,
                        Dish::getCalories,
                        Integer::sum)
                );
    }

    public static int calculateTotalCaloriesWithoutCollectors() {

        return menu.stream()
                .map(Dish::getCalories)
                .reduce(0, (Integer::sum));
    }

    public static int calculateTotalCalories() {


        Integer calculateTotalCalories = menu.stream()
                .collect(reducing(0, (Dish dish) -> dish.getCalories(), (Integer i, Integer j) -> i + j));

        return calculateTotalCalories;

    }
}

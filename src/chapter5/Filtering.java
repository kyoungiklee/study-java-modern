package chapter5;

import chapter04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Filtering {

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


    public static final List<Dish> specialMenu = Arrays.asList(
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER));

    public static void main(String[] args) {
        //메뉴 리스트에서 채식주의자 메뉴를 출력하시오
        menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println();

        //주어진 숫자 리스트에서 2의 배수만 고유값으로 출력하시오
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println();

        //스페셜 메뉴에서 칼로리가 320이하인 것을 출력하시오
        System.out.println("Filtered sorted menu:");
        specialMenu.stream()
                .filter(specialMenu -> specialMenu.getCalories() < 320)
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println();

        //메뉴 리스트에서 칼로리가 300보다 높은 메뉴 중 3개반 출력하시오
        System.out.println("Truncating a stream:");
        menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println();

        System.out.println("Skipping elements:");
        menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }


}

package chapter5;

import chapter04.Dish;
import chapter04.Type;

import java.util.Arrays;
import java.util.List;

public class Reducing {

    public static final List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Type.MEAT),
            new Dish("beef", false, 700, Type.MEAT),
            new Dish("chicken", false, 400, Type.MEAT),
            new Dish("french fries", true, 530, Type.OTHER),
            new Dish("rice", true, 350, Type.OTHER),
            new Dish("season fruit", true, 120, Type.OTHER),
            new Dish("pizza", true, 550, Type.OTHER),
            new Dish("prawns", false, 400, Type.FISH),
            new Dish("salmon", false, 450, Type.FISH)
    );
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        //주어진 숫자 리스트의 합계를 구하시오
        System.out.println(numbers.stream().reduce(0, (a, b) -> a + b)
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
                .map(dish -> dish.getCalories())
                .reduce(0, Integer::sum));

    }
}

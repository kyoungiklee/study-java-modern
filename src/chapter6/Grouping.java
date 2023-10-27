package chapter6;

import chapter04.Dish;
import chapter04.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Grouping {

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
        System.out.println("Dishes grouped by type: " + groupDishesByType());
    }

    public static Map<Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(Collectors.groupingBy(new Function<Dish, Type>() {
            @Override
            public Type apply(Dish dish) {
                return dish.getType();
            }
        }));
    }
}

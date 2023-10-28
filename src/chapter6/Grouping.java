package chapter6;

import chapter04.Dish;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

public class Grouping {

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

    public static final Map<String, List<String>> dishTags = new HashMap<>();
    static {
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));
    }
    public static void main(String[] args) {
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dish names grouped by type: " + groupDishNamesByType());
        System.out.println("Dish tags grouped by type: " + groupDishTagsByType());
        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type first way: " + mostCaloricDishesByTypeFirst());
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByTypeFirst() {
        return menu.stream()
                .collect(groupingBy(Dish::getType, maxBy(Comparator.comparingInt(Dish::getCalories))));
    }

    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream().collect(groupingBy(Dish::getType, counting()));
    }


    private static Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {

        return menu.stream()
                .collect(groupingBy(Dish::getType, groupingBy(dish -> {
                    if (dish.getCalories() <= 300) return Dish.CaloricLevel.DIET;
                    else if (dish.getCalories() <= 500 ) return Dish.CaloricLevel.NORMAL;
                    else return Dish.CaloricLevel.FAT;
                }, toList())));
    }

    private static Map<Dish.CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {

        return menu.stream()
                .collect(groupingBy(dish -> {
                            if (dish.getCalories() <= 300) return Dish.CaloricLevel.DIET;
                            else if (dish.getCalories() <= 500) return Dish.CaloricLevel.NORMAL;
                            else return Dish.CaloricLevel.FAT;
                        }
                ));
    }

    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {

        return menu.stream()
                .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
    }

    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())
                ));
    }

    private static Map<Dish.Type, List<String>>  groupDishNamesByType() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        mapping(Dish::getName, toList())
                ));
    }

    public static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(groupingBy(Dish::getType));
    }
}

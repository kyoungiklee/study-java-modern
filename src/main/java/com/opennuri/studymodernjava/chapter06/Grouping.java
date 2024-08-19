package com.opennuri.studymodernjava.chapter06;



import com.opennuri.studymodernjava.chapter04.Dish;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
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
        System.out.println("Most caloric dishes by type using reducing: " + mostCaloricDishesByTypeUsingReducing());
        System.out.println("Most caloric dishes by type using maxby: " + mostCaloricDishesByTypeUsingMaxBy());
        System.out.println("Most caloric dishes by type using collectAndThen: " + mostCaloricDishesByTypeUsingCollectingAndThen());
        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
        System.out.println("Sorted dishes name grouped by type: " + groupByTypeAndSortByNameAfterSorted());
        System.out.println("Sorted dishes name grouped by type: " + groupByTypeAndSortByNameAfterGroping());

    }

    public static Map<Dish.Type, List<Dish>> groupByTypeAndSortByNameAfterGroping() {
        Map<Dish.Type, List<Dish>> collect = menu.stream()
                .collect(groupingBy(Dish::getType));

        collect.values()
                .forEach(list -> list.sort(comparing(Dish::getName)));

        return collect;

    }

    public static Map<Dish.Type, List<Dish>> groupByTypeAndSortByNameAfterSorted() {
        return menu
                .stream()
                .sorted(comparing(Dish::getName))
                .collect(Collectors.groupingBy(Dish::getType));
    }

    public static Map<Dish.Type, List<Dish.CaloricLevel>> caloricLevelsByType() {
        return menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping((Dish dish) -> {
                            if (dish.getCalories() < 300) return Dish.CaloricLevel.DIET;
                            else if (dish.getCalories() < 500) return Dish.CaloricLevel.NORMAL;
                            return Dish.CaloricLevel.FAT;
                        }, toList())
                ));
    }

    public static Map<Dish.Type, Integer> sumCaloriesByType() {
         return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        summingInt(Dish::getCalories)
                ));
    }

    public static Map<Dish.Type, Integer> mostCaloricDishesByTypeUsingCollectingAndThen() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        collectingAndThen(reducing((Dish dish1, Dish dish2)
                                        -> dish1.getCalories() > dish2.getCalories() ? dish1 : dish2),
                                dish -> dish.map(Dish::getCalories).orElse(0))));
    }

    public static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByTypeUsingReducing() {
       return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)
                ));
    }

    public static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByTypeUsingMaxBy() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        maxBy(comparingInt(Dish::getCalories))
                ));
    }

    public static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream()
                .collect(groupingBy(
                    Dish::getType,
                    counting()
                ));
    }


    public static Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {

        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        groupingBy(dish -> {
                    if (dish.getCalories() <= 300) return Dish.CaloricLevel.DIET;
                    else if (dish.getCalories() <= 500 ) return Dish.CaloricLevel.NORMAL;
                    else return Dish.CaloricLevel.FAT;
                }, toList())
                ));
    }

    public static Map<Dish.CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {

        return menu.stream()
                .collect(groupingBy(
                        dish -> {
                            if (dish.getCalories() <= 300) return Dish.CaloricLevel.DIET;
                            else if (dish.getCalories() <= 500) return Dish.CaloricLevel.NORMAL;
                            else return Dish.CaloricLevel.FAT;
                        }
                ));
    }

    public static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {

        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        Collectors.filtering(dish -> dish.getCalories() > 500, toList())
                ));
    }

    public static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())
                ));
    }

    public static Map<Dish.Type, List<String>>  groupDishNamesByType() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        mapping(Dish::getName, toList())
                ));
    }

    public static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(groupingByConcurrent(Dish::getType));
    }
}

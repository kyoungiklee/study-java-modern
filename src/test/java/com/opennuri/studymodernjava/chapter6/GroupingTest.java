package com.opennuri.studymodernjava.chapter6;

import com.opennuri.studymodernjava.chapter04.Dish;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;


@Slf4j
class GroupingTest {

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

    @Test
    @DisplayName(value = "메뉴 타입으로 그룹핑하기")
    void groupDishesByTypeTest() {

        Map<Dish.Type, List<Dish>> typeListMap = Grouping.groupDishesByType();
        assertThat(typeListMap.keySet())
                .hasSize(3)
                .containsExactlyInAnyOrder(Dish.Type.OTHER, Dish.Type.FISH, Dish.Type.MEAT);

        assertThat(typeListMap.get(Dish.Type.MEAT)).hasSize(3)
                .extracting("name", "calories")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("chicken", 400),
                        Tuple.tuple("pork", 800),
                        Tuple.tuple("beef", 700)
                );

        @SuppressWarnings(value = "training")
        Map<Dish.Type, List<Dish>> groupDishesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType));
        log.debug("메뉴 타입으로 그룹핑하기: {}", groupDishesByType);

    }

    @Test
    @DisplayName(value = "메뉴 타입으로 분류하고 분류별 이름순으로 정렬하기")
    void groupByTypeAndSortByNameAfterGroping() {

        Map<Dish.Type, List<Dish>> typeListMap = Grouping.groupByTypeAndSortByNameAfterGroping();

        //Filtering with Basic Conditions
        assertThat(typeListMap.get(Dish.Type.MEAT))
                .filteredOn(dish -> dish.getName().equals("beef")).isNotEmpty();

        //Filtering with Multiple Basic Conditions
        assertThat(typeListMap.get(Dish.Type.MEAT))
                .filteredOn(dish -> dish.getName().contains("beef") && dish.getCalories() == 700)
                .hasSize(1);

        //Field Filtering with Basic Condition
        assertThat(typeListMap.get(Dish.Type.MEAT))
                .filteredOn("name", "beef")
                .isNotEmpty();

        //Field Filtering with Complex Conditions
        assertThat(typeListMap.get(Dish.Type.MEAT))
                .filteredOn("name", in("beef", "pork", "chicken"))
                .filteredOn(dish -> dish.getCalories() > 400)
                .hasSize(2);

        //Predicate Filtering with Null Values
        List<Dish> collect = (List<Dish>) menu.stream()
                .map(dish -> {
                        if (dish.isVegetarian()) {
                            return Dish.builder()
                                    .type(dish.getType())
                                    .name(dish.getName())
                                    .calories(dish.getCalories())
                                    .isVegetarian(false)
                                    .build();
                        }
                        return dish;
                    })
                .toList();
        assertThat(collect)
                .filteredOn(Dish::isVegetarian).isEmpty();

        @SuppressWarnings(value = "training")
        Map<Dish.Type, List<Dish>> groupingByDishType = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        groupingByDishType.values()
                .forEach(list-> list.sort(Comparator.comparing(Dish::getName)));

        log.debug("메뉴 타입으로 분류하고 분류별 이름순으로 정렬하기: {}", groupingByDishType );

    }

    @Test
    @DisplayName(value = "메뉴 정렬 후 메뉴 타입으로 분류하기")
    void groupByTypeAndSortByNameAfterSorted() {

        Map<Dish.Type, List<Dish>> typeListMap = Grouping.groupByTypeAndSortByNameAfterSorted();
        assertThat(typeListMap.get(Dish.Type.MEAT))
                .filteredOn("name", in("beef", "chicken", "pork"))
                .hasSize(3);

        @SuppressWarnings(value = "training")
        Map<Dish.Type, List<Dish>> collect = menu.stream()
                .sorted(Comparator.comparing(Dish::getName))
                .collect(Collectors.groupingBy(Dish::getType));
        log.debug("메뉴 정렬 후 메뉴 타입으로 분류하기: {}", collect);
    }

    @Test
    @DisplayName(value = "메뉴 타입으로 분류하고 각 메뉴의 다이어트 레벨 적용")
    void caloricLevelsByType() {
        Map<Dish.Type, List<Dish.CaloricLevel>> collect = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(dish -> {
                            if (dish.getCalories() < 300)
                                return Dish.CaloricLevel.DIET;
                            else if (dish.getCalories() < 700)
                                return Dish.CaloricLevel.NORMAL;
                            else
                                return Dish.CaloricLevel.FAT;
                        }, Collectors.toList()))
                );
        log.debug("메뉴 타입으로 분류하고 각 메뉴의 다이어트 레벨 적용: {}", collect);
    }

    @Getter
    @Setter
    @ToString
    public static class DishLevel {
        private Dish dish;
        private Dish.CaloricLevel level;

        @Builder
        public DishLevel(Dish dish, Dish.CaloricLevel level) {
            this.dish = dish;
            this.level = level;
        }
    }

    @Test
    void sumCaloriesByType() {
    }

    @Test
    void mostCaloricDishesByTypeWithoutOptionals() {
    }

    @Test
    void mostCaloricDishesByTypeSecond() {
    }

    @Test
    void mostCaloricDishesByTypeFirst() {
    }

    @Test
    void countDishesInGroups() {
    }

    @Test
    void groupDishedByTypeAndCaloricLevel() {
    }

    @Test
    void groupDishesByCaloricLevel() {
    }

    @Test
    void groupCaloricDishesByType() {
    }

    @Test
    void groupDishTagsByType() {
    }

    @Test
    void groupDishNamesByType() {
    }

    @Test
    void groupDishesByType() {
    }


    @Getter
    @Setter
    @ToString
    public static class Student {
        private String name;
        private int age;

        @Builder
        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }


    public static List<Student> getStudentSample(int max) {
        return IntStream.range(0, max)
                .mapToObj(i -> new Student("name" + i, i + 10))
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName(value = "java strema 에서의 맵 사용")
    void convertListToMap() {
        int max = 3;
        List<Student> students = getStudentSample(max);
        Map<String, Integer> studentMap = new HashMap<>();

        Student student;
        for(int i = 0; i < students.size(); i++) {
            student = students.get(i);
            studentMap.put(student.name, student.age);
        }
        assertThat(studentMap).size().isEqualTo(max);
        log.debug("student map value: {}", studentMap);

        Map<String, Integer> collect = students.stream()
                .collect(Collectors.toMap(
                        Student::getName, Student::getAge)
                );
        assertThat(collect.size()).isEqualTo(3);
        log.debug("student map value: {}", collect);


    }
}
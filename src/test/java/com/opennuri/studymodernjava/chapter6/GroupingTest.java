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

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;
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
        Map<Dish.Type, List<Dish.CaloricLevel>> typeListMap = Grouping.caloricLevelsByType();

        assertThat(typeListMap.get(Dish.Type.MEAT))
                .contains(Dish.CaloricLevel.FAT, Dish.CaloricLevel.NORMAL);

        assertThat(typeListMap.get(Dish.Type.FISH))
                .contains(Dish.CaloricLevel.NORMAL);



        @SuppressWarnings(value = "training")
        Map<Dish.Type, List<DishLevel>> collect = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(dish -> {
                            if (dish.getCalories() < 300)
                                return DishLevel.builder().level(Dish.CaloricLevel.DIET).dish(dish).build();
                            else if (dish.getCalories() < 700)
                                return DishLevel.builder().level(Dish.CaloricLevel.NORMAL).dish(dish).build();
                            else
                                return DishLevel.builder().level(Dish.CaloricLevel.FAT).dish(dish).build();
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
    @DisplayName(value = "메뉴별 칼로리 합계 출력하기")
    void sumCaloriesByType() {

        Map<Dish.Type, Integer> typeIntegerMap = Grouping.sumCaloriesByType();
        assertThat(typeIntegerMap.keySet()).hasSize(3);
        assertThat(typeIntegerMap.entrySet().stream().mapToInt(Map.Entry::getValue)).contains(1900, 850, 1550);

        @SuppressWarnings(value = "training")
        Map<Dish.Type, Integer> collect = menu.stream()
                .collect(groupingBy(Dish::getType, this.summingInt(new ToIntFunction<Dish>() {
                            @Override
                            public int applyAsInt(Dish dish) {
                                return dish.getCalories();
                            }
                        })
                ));
        log.debug("메뉴별 칼로리 합계 출력하기(summingIng사용): {}", collect);

        Map<Dish.Type, Integer> collect2 = menu.stream().collect(groupingBy(Dish::getType,
                reducing(0, //초기값
                        new Function<Dish, Integer>() { //mapper -> 합계를 만들기 위해 Integer의 스트림으로 변환
                            @Override
                            public Integer apply(Dish dish) {
                                return dish.getCalories();
                            }
                        },
                        new BinaryOperator<Integer>() { // collect 함수 ->  Integer 스트림에 적용활 함수
                            @Override
                            public Integer apply(Integer integer, Integer integer2) {
                                return integer + integer2;
                            }
                        }
                )
        ));
        log.debug("메뉴별 칼로리 합계 출력하기(reducing사용): {}", collect2);

        Map<Dish.Type, Integer> collect3 = menu.stream().collect(groupingBy(Dish::getType,
                reducing( // 함수형으로 변환
                        0,
                        (Dish dish) -> dish.getCalories(),
                        (Integer i1, Integer i2) -> (i1 + i2)
                )
        ));
        log.debug("메뉴별 칼로리 합계 출력하기(reducing사용): {}", collect3);

        Map<Dish.Type, Integer> collect1 = menu.stream().collect(groupingBy(Dish::getType,
                reducing(0,
                        Dish::getCalories, //메소드 레퍼런스로 변환
                        Integer::sum))); //메소드 레퍼런스로 변환
        log.debug("메뉴별 칼로리 합계 출력하기(reducing사용): {}", collect1);
    }

    //Collectors 에서 가져옴
    static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();
    public static <T> Collector<T, ?, Integer> summingInt(ToIntFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new int[1],
                (a, t) -> { a[0] += mapper.applyAsInt(t); },
                (a, b) -> { a[0] += b[0]; return a; },
                a -> a[0], CH_NOID);
    }
    record CollectorImpl<T, A, R>(Supplier<A> supplier,
                                  BiConsumer<A, T> accumulator,
                                  BinaryOperator<A> combiner,
                                  Function<A, R> finisher,
                                  Set<Characteristics> characteristics
    ) implements Collector<T, A, R> {
        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }
    }



    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    @Test
    void mostCaloricDishesByTypeUsingCollectingAndThen() {

        Map<Dish.Type, Integer> typeIntegerMap = Grouping.mostCaloricDishesByTypeUsingCollectingAndThen();
        assertThat(typeIntegerMap.get(Dish.Type.MEAT)).isEqualTo(800);

        @SuppressWarnings(value = "traning")
        Map<Dish.Type, Integer> mostCalorie = menu.stream().collect(groupingBy(Dish::getType,
                collectingAndThen(reducing(new BinaryOperator<Dish>() {
                            @Override
                            public Dish apply(Dish dish, Dish dish2) {
                                return dish.getCalories() > dish2.getCalories() ? dish : dish2;
                            }
                        }), new Function<Optional<Dish>, Integer>() {

                            @Override
                            public Integer apply(Optional<Dish> dish) {
                                return dish.map(Dish::getCalories).orElse(0);
                            }
                        }
                )
        ));
        log.debug("메뉴별 칼로리가 가장 높은 칼로리 찾기: {}", mostCalorie);

    }

    @Test
    @DisplayName(value = "메뉴별 칼로리가 가장 높은 칼로리 찾기(reducing 사용)")
    void mostCaloricDishesByTypeUsingReducing() {
        Map<Dish.Type, Optional<Dish>> typeOptionalMap = Grouping.mostCaloricDishesByTypeUsingReducing();
        assertThat(typeOptionalMap.get(Dish.Type.MEAT).get().getCalories()).isEqualTo(800);

        @SuppressWarnings(value = "training")
        Map<Dish.Type, Integer> collect = menu.stream().collect(groupingBy(Dish::getType, reducing(0, new Function<Dish, Integer>() {
                            @Override
                            public Integer apply(Dish dish) {
                                return dish.getCalories();
                            }
                        }, new BinaryOperator<Integer>() {
                            @Override
                            public Integer apply(Integer integer, Integer integer2) {
                                return integer > integer2 ? integer : integer2;
                            }
                        }
                )
        ));
        log.debug("메뉴별 칼로리가 가장 높은 칼로리 찾기(reducing 사용): {}", collect);

        //메소드 레퍼런스 사용
        Map<Dish.Type, Integer> collect1 = menu.stream().collect(groupingBy(Dish::getType,
                reducing(0, Dish::getCalories, Integer::max)));
        log.debug("메뉴별 칼로리가 가장 높은 칼로리 찾기(reducing 사용): {}", collect1);

    }

    @Test
    @DisplayName(value = "메뉴별 칼로기가 가장 높은 칼로 찾기(maxBy 사용)")
    void mostCaloricDishesByTypeUsingMaxBy() {

        Map<Dish.Type, Optional<Dish>> typeOptionalMap = Grouping.mostCaloricDishesByTypeUsingMaxBy();
        assertThat(typeOptionalMap.get(Dish.Type.MEAT).get().getCalories()).isEqualTo(800);

        @SuppressWarnings(value = "taining")
        Map<Dish.Type, Optional<Dish>> collect = menu.stream()
                .collect(groupingBy(Dish::getType, maxBy(Comparator.comparing(Dish::getCalories))));
        log.debug("메뉴별 칼로기가 가장 높은 칼로 찾기(maxBy 사용): {}", collect);
    }

    @Test
    @DisplayName(value = "메뉴 타입별 갯수 출력하기")
    void countDishesInGroups() {
        @SuppressWarnings(value = "training")
        Map<Dish.Type, Long> collect = menu.stream().collect(groupingBy(Dish::getType, counting()));
        log.debug("메뉴 타입별 갯수 출력하기: {}", collect);
    }

    @Test
    @DisplayName(value = "메뉴 타입과 칼로리 레벨로 그룹핑하기")
    void groupDishedByTypeAndCaloricLevel() {

        Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> typeMapMap = Grouping.groupDishedByTypeAndCaloricLevel();
        assertThat(typeMapMap.get(Dish.Type.MEAT).get(Dish.CaloricLevel.FAT)).isNotEmpty()
                .extracting("name")
                .contains("pork", "beef");

        assertThat((typeMapMap.get(Dish.Type.MEAT).get(Dish.CaloricLevel.FAT)))
                .filteredOn("name", in("pork", "beef"))
                .isNotEmpty()
                .hasSize(2);


        @SuppressWarnings(value = "training")
        Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> collect = menu.stream().collect(groupingBy(Dish::getType, groupingBy(new Function<Dish, Dish.CaloricLevel>() {
            @Override
            public Dish.CaloricLevel apply(Dish dish) {
                if (dish.getCalories() < 300) return Dish.CaloricLevel.DIET;
                else if (dish.getCalories() < 500) {
                    return Dish.CaloricLevel.NORMAL;
                } else return Dish.CaloricLevel.FAT;
            }
        })));
        log.debug("메뉴 타입과 칼로리 레벨로 그룹핑하기: {}", collect);

    }

    @Test
    @DisplayName(value = "메뉴를 칼로리 레벨로 그룹핑하기")
    void groupDishesByCaloricLevel() {

        Map<Dish.CaloricLevel, List<Dish>> caloricLevelListMap = Grouping.groupDishesByCaloricLevel();
        assertThat(caloricLevelListMap.get(Dish.CaloricLevel.FAT)).isNotEmpty()
                .hasSize(4)
                .extracting("name")
                .contains("pork", "beef", "french fries", "pizza");

        @SuppressWarnings(value = "training")
        Map<Dish.CaloricLevel, List<Dish>> collect = menu.stream().collect(groupingBy(new Function<Dish, Dish.CaloricLevel>() {
            @Override
            public Dish.CaloricLevel apply(Dish dish) {
                if (dish.getCalories() < 300) return Dish.CaloricLevel.DIET;
                else if (dish.getCalories() < 500) return Dish.CaloricLevel.NORMAL;
                else return Dish.CaloricLevel.FAT;
            }
        }));
        log.debug("메뉴를 칼로리 레벨로 그룹핑하기: {}", collect);

    }

    @Test
    @DisplayName(value = "메뉴 타입별로 칼로리가 500이상인 메뉴 찾기")
    void groupCaloricDishesByType() {
        Map<Dish.Type, List<Dish>> collect = menu.stream().collect(groupingBy(Dish::getType, filtering(
                new Predicate<Dish>() {
                    @Override
                    public boolean test(Dish dish) {
                        return dish.getCalories() > 500;
                    }
                }, toList()
        )));

        log.debug("메뉴 타입별로 칼로리가 500이상인 메뉴 찾기: {}", collect);

        //그룹핑 후 필터링을 사용하여 그룹내의 값을 조건으로 찾을 수 있다.
        Map<Dish.Type, List<Dish>> collect1 = menu.stream().collect(groupingBy(Dish::getType,
                filtering(dish -> dish.getCalories() > 500, toList())));
        log.debug("메뉴 타입별로 칼로리가 500이상인 메뉴 찾기: {}", collect1);
        
    }

    @Test
    @DisplayName(value = "메뉴 타입별로 메뉴 태그 찾기(Set)")
    void groupDishTagsByType() {
        Map<Dish.Type, Set<String>> typeSetMap = Grouping.groupDishTagsByType();
        assertThat(typeSetMap.get(Dish.Type.MEAT)).hasSize(5)
                .containsExactlyInAnyOrder("salty", "greasy", "roasted", "fried", "crisp");

        @SuppressWarnings(value = "taining")
        Map<Dish.Type, Set<String>> collect = menu.stream().collect(groupingBy(Dish::getType,
                flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())
        ));
        log.debug("메뉴 타입별로 메뉴 태그 찾기(Set): {}", collect);
    }

    @Test
    @DisplayName(value = "메뉴 타입별로 메뉴명 출력하기")
    void groupDishNamesByType() {
        Map<Dish.Type, List<String>> typeListMap = Grouping.groupDishNamesByType();
        assertThat(typeListMap.get(Dish.Type.FISH)).isNotEmpty()
                .contains("prawns", "salmon");

        @SuppressWarnings(value = "training")
        Map<Dish.Type, List<String>> collect = menu.stream().collect(groupingBy(Dish::getType,
                mapping(Dish::getName, toList())));
        log.debug("메뉴 타입별로 메뉴명 출력하기: {}", collect);
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
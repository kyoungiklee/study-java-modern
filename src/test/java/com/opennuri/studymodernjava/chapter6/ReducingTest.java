package com.opennuri.studymodernjava.chapter6;

import com.opennuri.studymodernjava.chapter04.Dish;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summingInt;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ReducingTest {

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

    @Test
    @DisplayName(value = "reducing() 이용하여 메뉴의 칼로리 총합을 구한다")
    void calculateTotalCaloriesTest() {
        assertThat(Reducing.calculateTotalCalories()).isEqualTo(4300);

        @SuppressWarnings(value = "training")
        Integer reduce = menu.stream().reduce(0, new BiFunction<Integer, Dish, Integer>() {
            @Override
            public Integer apply(Integer integer, Dish dish) {
                return integer + dish.getCalories();
            }
        }, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        });

        log.debug("reduce를 이용하여 메뉴의 칼로리 총합 구하기: {}" , reduce);

        //lamda 형식으로 간략화
        Integer reduce1 = menu.stream().map(Dish::getCalories)
                .reduce(0, (integer, integer2) -> integer + integer2);
        log.debug("reduce를 이용하여 메뉴의 칼로리 총합 구하기(lamda): {}", reduce1);

        //메소드 레퍼런스 형식으로 간략화
        int reduce2 = menu.stream()
                .mapToInt(Dish::getCalories)
                .reduce(0, Integer::sum);
        log.debug("reduce를 이용하여 메뉴의 칼로리 총합 구하기(lamda): {}", reduce2);

        Integer collect = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        log.debug("collectors.reducing 이용하여 메뉴의 칼로리 총합 구하기: {}", collect);
    }

    @Test
    @DisplayName(value = "reducing()에서 메소드 레퍼런스를 사용하여 메뉴 칼로리 총합을 구한다")
    void calculateTotalCaloriesWithMethodReferenceUsingReducing() {
        assertThat(Reducing.calculateTotalCaloriesWithMethodReferenceUsingReducing()).isEqualTo(4300);

        @SuppressWarnings(value = "training")
        Integer collect = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        log.debug("reducing()에서 메소드 레퍼런스를 사용하여 메뉴 칼로리 총합을 구한다: {}", collect);
    }

    @Test
    @DisplayName(value = "reducing() 메소드 사용하지 않고 메뉴의 칼로리 총합을 구한다")
    void calculateTotalCaloriesWithMethodReferenceTest() {
        assertThat(Reducing.calculateTotalCaloriesWithoutCollectors()).isEqualTo(4300);

        @SuppressWarnings(value = "taining")
        Integer reduce = menu.stream().map(dish -> dish.getCalories())
                .reduce(0, (integer, integer2) -> integer + integer2);
        log.debug("reducing() 메소드 사용하지 않고 메뉴의 칼로리 총합 구하기: {}", reduce);

        assertThat(reduce).isEqualTo(4300);

    }

    @Test
    @DisplayName(value = "Sum 메소드를 이용하여 합계 구하기")
    void calculateTotalCaloriesUsingSum() {
        assertThat(Reducing.calculateTotalCaloriesUsingSum()).isEqualTo(4300);

        Integer collect = menu.stream().collect(summingInt(Dish::getCalories));
        log.debug("Sum 메소드를 이용하여 합계 구하기: {}", collect);
    }
}
package com.opennuri.studymodernjava.chapter5;

import com.opennuri.studymodernjava.chapter04.Dish;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ReducingTest {

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

    @Test
    @DisplayName(value = "메뉴의 칼로리 총합 구하기")
    void sumDishCalories() {
        Integer reduce = menu.stream().reduce(0,
                (integer, dish) -> integer + dish.getCalories(),
                (calorie1, calorie2) -> calorie1 + calorie2);
        log.debug("메뉴의 칼로리 총합 구하기: {}", reduce);

        assertThat(reduce).isEqualTo(4300);
    }
}
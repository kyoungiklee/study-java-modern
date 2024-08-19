package com.opennuri.studymodernjava.chapter05;

import com.opennuri.studymodernjava.chapter04.Dish;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class FilteringTest {

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


    //메뉴 리스트에서 채식주의자 메뉴를 출력하시오
    @Test
    @DisplayName("재식주의자 메뉴 출력")
    void print_vegetarian_menu() {
        menu.stream().filter(Dish::isVegetarian)
                .toList()
                .forEach(dish -> log.info(dish.toString()));
        //todo assertThat으로 변경

    }

    //주어진 숫자 리스트에서 2의 배수만 고유값으로 출력하시오
    @Test
    @DisplayName("숫자 리스트에서 2의 배수만 출력")
    void only_multiples_of_two_in_numbers() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(number -> number % 2 == 0)
                .distinct()
                .forEach(number -> log.info(number.toString()));
        //todo assertThat으로 변경

    }

    //스페셜 메뉴에서 칼로리가 320이하인 것을 출력하시오
    @Test
    @DisplayName("스페셜 메뉴에서 칼로리가 320 이하인 메뉴 출력")
    void print_menus_with_calories_less_than_320_from_special_menus() {
        specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .toList()
                .forEach(dish -> log.info(dish.toString()));

        //todo assertThat으로 변경
    }

    //메뉴 리스트에서 칼로리가 300보다 높은 메뉴 중 3개만
    @Test
    @DisplayName("칼로기가 300보다 큰 메뉴에서 3개만 출력")
    void only_3_menu_items_with_calories_higher_than_300() {
        List<Dish> list = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .toList();
        list.forEach(dish -> log.info(dish.toString()));

        Dish pork = new Dish("pork", false, 800, Dish.Type.MEAT);
        Dish beef = new Dish("beef", false, 700, Dish.Type.MEAT);
        Dish chicken = new Dish("chicken", false, 400, Dish.Type.MEAT);

        assertThat(list.size()).isEqualTo(3);
        List<String> menuNames = list.stream().map(Dish::getName).toList();
        assertThat(menuNames).containsExactlyInAnyOrder("pork", "beef", "chicken");

    }

    //todo
    //메뉴 리스트에서 칼로리가 300보다 높은 메뉴중 2개를 제외하고 출력하시오
    @Test
    void print_excluding_2_menu_items_with_calories_higher_than_300() {

    }





}
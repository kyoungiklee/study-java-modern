package com.opennuri.studymodernjava.chapter05;

import com.opennuri.studymodernjava.chapter04.Dish;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@Slf4j
class MappingTest {

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

    //메뉴명을 출력하시오
    @Test
    @DisplayName("메뉴 리스트에서 메뉴명 출력")
    void print_menu_names() {
        List<String> list = menu.stream().map(Dish::getName)
                .toList();

        list.forEach(log::info);

        // list 의 갯수는 9개이다.
        assertThat(list.size()).isEqualTo(9);
        // list의 스트링 값을 확인한다.
        assertThat(list).contains("pork", "beef", "chicken", "french fries", "rice", "season fruit", "pizza", "prawns", "salmon");
    }

    //스트링 리스트의 스트링 길이의 합을 출력하시오
    @Test
    @DisplayName("스트링 리스트의 문자열 길이의 합")
    void print_sum_of_string_array() {
        List<String> words = Arrays.asList("Hello", "World");

        Integer reduce = words.stream().map(String::length)
                .reduce(0, Integer::sum);

        assertThat(reduce).isEqualTo(10);
    }

    //스트링 리스트에서 고유한 문자를 출력하시오
    @Test
    @DisplayName("문자열에서 고유한 문자 출력")
    void print_unique_characters_from_string() {
        List<String> words = Arrays.asList("Hello", "World");
        List<String> list = words.stream()
                .flatMap(s -> Arrays.stream(s.split("")))
                .distinct()
                .toList();
        list.forEach(log::info);
        assertThat(list.size()).isEqualTo(7);
    }

    //주어진 숫자 리스트를 숫자 짝의 합이 3의 배수인 값을 출력하시오. 출력형태: (1, 8),......,(5,7)
    @Test
    @DisplayName("숫자 리스트를 숫자 짝의 합이 3의 배수인 값을 출력")
    void print_sum_of_the_number_pairs_is_a_multiple_of_3() {
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);

        // 중첩 for문 사용
        List<int[]> intArrayList = new ArrayList<>();

        for (Integer integer : numbers1) {
            for (Integer item : numbers2) {
                if ((integer + item) % 3 == 0) {
                    int[] value = new int[2];
                    value[0] = integer;
                    value[1] = item;
                    intArrayList.add(value);

                }
            }
        }

        intArrayList.forEach(item -> log.info("({},{})", item[0], item[1]));
        assertThat(intArrayList.size()).isEqualTo(5);

        // stream 사용
        List<int[]> list = numbers1.stream().flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                .filter(array -> (array[0] + array[1]) % 3 == 0)
                .toList();
        list.forEach(item -> log.info("({},{})", item[0], item[1]));
        assertThat(list.size()).isEqualTo(5);
    }
}
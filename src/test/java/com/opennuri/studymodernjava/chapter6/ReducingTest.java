package com.opennuri.studymodernjava.chapter6;

import com.opennuri.studymodernjava.chapter6.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReducingTest {

    @Test
    @DisplayName(value = "reducing() 이용하여 메뉴의 칼로리 총합을 구한다")
    void calculateTotalCaloriesTest() {
        assertThat(Reducing.calculateTotalCalories()).isEqualTo(4300);
    }

    @Test
    @DisplayName(value = "reducing()에서 메소드 레퍼런스를 사용하여 메뉴 칼로리 총합을 구한다")
    void calculateTotalCaloriesWithMethodReferenceUsingReducing() {
        assertThat(Reducing.calculateTotalCaloriesWithMethodReferenceUsingReducing()).isEqualTo(4300);

    }

    @Test
    @DisplayName(value = "reducing() 메소드 사용하지 않고 메뉴의 칼로리 총합을 구한다")
    void calculateTotalCaloriesWithMethodReferenceTest() {
        assertThat(Reducing.calculateTotalCaloriesWithoutCollectors()).isEqualTo(4300);

    }

    @Test
    @DisplayName(value = "Sum 메소드를 이용하여 합계 구하기")
    void calculateTotalCaloriesUsingSum() {
        assertThat(Reducing.calculateTotalCaloriesUsingSum()).isEqualTo(4300);
    }
}
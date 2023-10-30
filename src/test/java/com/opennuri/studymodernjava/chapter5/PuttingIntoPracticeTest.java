package com.opennuri.studymodernjava.chapter5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PuttingIntoPracticeTest {

    @Test
    @DisplayName(value = "거래내역 중 최소값 확인")
    void findMinValueInAllTransactionsUsingComparing() {
        assertThat(PuttingIntoPractice.findMinValueInAllTransactionsUsingComparing().getValue())
                .isEqualTo(300);
    }

    @Test
    @DisplayName(value = "거래자가 거주하는 도시")
    void findMinValueInAllTransactions() {
    }

    @Test
    void findMaxValueInAllTransactionsUsingReduce() {
    }

    @Test
    void findMaxValueInAllTransactions() {
    }

    @Test
    void findAllTransactionsOfTradersInCambridge() {
    }

    @Test
    void isAnyTradersBasedInMilan() {
    }

    @Test
    void findStringOfNamesAllTradersSortedAlphabetically() {
    }

    @Test
    void findAllTradersInCambridgeAndSortByname() {
    }

    @Test
    void findAllUniqueCitiesWhereTradersWork() {
    }

    @Test
    void findTransactionsByYear() {
    }
}
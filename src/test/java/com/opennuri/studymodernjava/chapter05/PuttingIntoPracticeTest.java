package com.opennuri.studymodernjava.chapter05;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PuttingIntoPracticeTest {


    private static List<Transaction> transactions;
    @BeforeEach
    void setUp() {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2010, 400),
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

    }

    @Test
    @DisplayName(value = "거래내역 중 최소값인 트랜잭션 조회")
    void findMinValueInAllTransactionsUsingComparing() {

        assertThat(PuttingIntoPractice.findMinValueInAllTransactionsUsingComparing())
                .isNotNull()
                .extracting("year","value" )
                .contains(2011, 300);

        @SuppressWarnings(value = "training")
        Transaction transaction = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue))
                .orElseThrow(IllegalStateException::new);

        log.debug("거래내역 중 최소값인 트랜잭션 조회: {}", transaction);

    }

    @Test
    @DisplayName(value = "거래자가 거주하는 도시를 유일한 값으로 확인")
    void findAllUniqueCitiesWhereTradersWork() {
        assertThat(PuttingIntoPractice.findAllUniqueCitiesWhereTradersWork())
                .isNotNull()
                .hasSize(2)
                .contains("Cambridge", "Milan" );

        @SuppressWarnings(value = "training")
        List<String> cities = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .toList();
        cities.forEach(log::debug);
    }

    @Test
    @DisplayName(value = "Cambridge에 사는 거래자의 모든 거래내역 출력")
    void findAllTransactionsOfTradersInCambridge() {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

         List<Transaction> expectedTransactions = Arrays.asList(
                 new Transaction(alan, 2012, 950),
                 new Transaction(brian, 2010, 400),
                 new Transaction(brian, 2011, 300),
                 new Transaction(raoul, 2012, 1000),
                 new Transaction(raoul, 2011, 400)

        );

        List<Transaction> allTransactionsOfTradersInCambridge
                = PuttingIntoPractice.findAllTransactionsOfTradersInCambridge();
        assertThat(allTransactionsOfTradersInCambridge)
                .isNotNull()
                .hasSize(5)
                .containsExactlyInAnyOrderElementsOf(expectedTransactions);

        @SuppressWarnings(value = "training")
        List<Transaction> cambrigeTranasctons = transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .toList();
        cambrigeTranasctons.forEach(transaction -> log.debug(transaction.toString()));
    }

    @Test
    @DisplayName(value = "모든거래에서 최소값 찾기")
    void findMinValueInAllTransactions() {
        Transaction minValueInAllTransactions = PuttingIntoPractice.findMinValueInAllTransactions();
        assertThat(minValueInAllTransactions)
                .isNotNull()
                .extracting("year", "value")
                .contains(2011, 300);

        @SuppressWarnings(value = "training")
        int minValue = transactions.stream()
                .mapToInt(Transaction::getValue)
                .min()
                .orElseThrow(IllegalArgumentException::new);
        log.debug("모든거래에서 최소값 찾기: {}", minValue);

    }

    @Test
    @DisplayName(value = "모든 거래에서 최고값 찾기(리듀스 이용)")
    void findMaxValueInAllTransactionsUsingReduce() {
        int maxValueInAllTransactionsUsingReduce
                = PuttingIntoPractice.findMaxValueInAllTransactionsUsingReduce();
        assertThat(maxValueInAllTransactionsUsingReduce).isEqualTo(1000);

        @SuppressWarnings(value = "training")
        int reduceMax = transactions.stream()
                .mapToInt(Transaction::getValue)
                .reduce(0, Integer::max);

        log.debug("모든 거래에서 최고값 찾기(리듀스 이용): {}", reduceMax);

    }

    @Test
    @DisplayName(value = "모든 거래에서 최고값 찾기")
    void findMaxValueInAllTransactions() {

        int maxValueInAllTransactions
                = PuttingIntoPractice.findMaxValueInAllTransactions();

        assertThat(maxValueInAllTransactions)
                .isEqualTo(1000);

        @SuppressWarnings(value = "training")
        int asInt = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max().orElseThrow(IllegalArgumentException::new);
        log.debug("모든 거래에서 최고값 찾기: {} ", asInt);
    }


    @Test
    @DisplayName(value = "Milan에 거주하는 거래자가 있는가?")
    void isAnyTradersBasedInMilan() {

        assertThat(PuttingIntoPractice.isAnyTradersBasedInMilan()).isTrue();

        @SuppressWarnings(value = "training" )
        boolean isBasedInMilan = transactions.stream()
                .map(Transaction::getTrader)
                .anyMatch(trader -> "Milan".equals(trader.getCity()));
        log.debug("Milan에 거주하는 거래자가 있는가: " + isBasedInMilan);
    }

    @Test
    @DisplayName(value = "거래자의 이름을 정렬하여 나열하기")
    void findStringOfNamesAllTradersSortedAlphabetically() {
        List<String> stringOfNamesAllTraders
                = PuttingIntoPractice.findStringOfNamesAllTradersSortedAlphabetically();

        assertThat(stringOfNamesAllTraders)
                .isNotNull()
                .hasSize(4)
                .contains("Raoul", "Mario", "Alan", "Brian")
                ;

        @SuppressWarnings(value = "training")
        List<String> traderNames = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .toList();
        traderNames.forEach(log::debug);

    }

    @Test
    @DisplayName(value = "Cambridge에 거주하는 모든 거래자 이름을 찾아 정렬")
    void findAllTradersInCambridgeAndSortByname() {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");

        assertThat(PuttingIntoPractice.findAllTradersInCambridgeAndSortByname())
                .isNotNull()
                .hasSize(3)
                .containsExactlyInAnyOrder(raoul, alan, brian);

        @SuppressWarnings(value = "training")
        List<Trader> collect = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                .sorted(Comparator.comparing(Trader::getName))
                .toList();
        collect.forEach(trader -> log.debug(trader.getName()));
    }

    @Test
    @DisplayName(value = "2011년부터 발생한 모든 거래를 찾아 값으로 정렬(작은 값에서 큰 값)")
    void findTransactionsByYear() {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        assertThat(PuttingIntoPractice.findTransactionsByYear())
                .isNotNull()
                .hasSize(6)
                .extracting("year", "value")
                .contains(
                        Tuple.tuple(2011, 300),
                        Tuple.tuple(2012, 950)
                );

        @SuppressWarnings(value = "training")
        List<Transaction> collects = transactions.stream()
                .filter(transaction -> transaction.getYear() >= 2012)
                .sorted(Comparator.comparing(Transaction::getValue))
                .toList();
        collects.forEach(transaction -> log.debug(transaction.toString()));
    }
}
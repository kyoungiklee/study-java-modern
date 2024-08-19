package com.opennuri.studymodernjava.chapter05;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PuttingIntoPractice {

    private static final Trader raoul = new Trader("Raoul", "Cambridge");
    private static final Trader mario = new Trader("Mario", "Milan");
    private static final Trader alan = new Trader("Alan", "Cambridge");
    private static final Trader brian = new Trader("Brian", "Cambridge");
    private static List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2010, 400),
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );
    public static void main(String[] args) {
        // 질의 1: 2011년부터 발생한 모든 거래를 찾아 값으로 정렬(작은 값에서 큰 값)
        System.out.println("Find transactions from 2011: " + findTransactionsByYear());
        // 질의 2: 거래자가 근무하는 모든 고유 도시
        System.out.println("Find all unique cities where traders work: " + findAllUniqueCitiesWhereTradersWork());
        // 질의 3: Cambridge에 거주하는 모든 거래자 이름을 찾아 정렬
        System.out.println("Find all traders name in Cambridge and sort by name: " + findAllTradersInCambridgeAndSortByname());
        // 질의 4: 모든 거래자의 이름 문자열을 알파벳 순으로 정렬
        System.out.println("Find string of names all traders in sorted alphabetically: " + findStringOfNamesAllTradersSortedAlphabetically());
        // 질의 5: Milan에 거주하는 거래자가 있는가?
        System.out.println("Is any traders based in Milan: " +isAnyTradersBasedInMilan());
        // 질의 6: Cambridge에 사는 거래자의 모든 거래내역 출력.
        System.out.println("Find all transactions of traders in Cambridge: " + findAllTransactionsOfTradersInCambridge());
        // 질의 7: 모든 거래에서 최고값은 얼마인가?
        System.out.println("Find max value in all transactions: " + findMaxValueInAllTransactions());
        System.out.println("Find max value in all transactions using reduce" + findMaxValueInAllTransactionsUsingReduce());
        // 질의 8: 가장 작은 값을 가진 거래 탐색
        System.out.println("Find min Transaction in all transactions: " + findMinValueInAllTransactions());
        System.out.println("Find min Transaction in all transactions using comparing: " + findMinValueInAllTransactionsUsingComparing());
    }

    public static Transaction findMinValueInAllTransactionsUsingComparing() {
        return transactions.stream()
                .min(comparing(Transaction::getValue))
                .orElseThrow();
    }

    public static Transaction findMinValueInAllTransactions() {
        return transactions.stream()
                .min((o1, o2) -> {
                    if(o1.getValue() > o2.getValue()) return 1;
                    else if (o1.getValue() < o2.getValue()) return -1;
                    else return 0;
                })
                .orElseThrow();
    }

    public static int findMaxValueInAllTransactionsUsingReduce() {
        return transactions.stream()
                .mapToInt(Transaction::getValue)
                .reduce(0, Integer::max);
    }

    public static int findMaxValueInAllTransactions() {
         return transactions.stream()
                 .mapToInt(Transaction::getValue)
                 .max()
                 .orElse(0);
    }

    public static List<Transaction> findAllTransactionsOfTradersInCambridge() {
         return transactions.stream()
                 .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                 .collect(Collectors.toList());
    }

    public static boolean isAnyTradersBasedInMilan() {
        return transactions.stream()
                .anyMatch(transaction -> "Milan".equals(transaction.getTrader().getCity()));
    }

    public static List<String> findStringOfNamesAllTradersSortedAlphabetically() {
         return transactions.stream()
                 .map(transaction -> transaction.getTrader().getName())
                 .distinct()
                 .sorted(String::compareTo)
                 .collect(Collectors.toList());
    }

    public static List<Trader> findAllTradersInCambridgeAndSortByname() {
          return transactions.stream()
                    .map(Transaction::getTrader)
                    .filter(trader -> trader.getCity().equals("Cambridge"))
                    .distinct()
                    .sorted(Comparator.comparing(Trader::getName))
                    .collect(Collectors.toList());
    }

    public static List<String> findAllUniqueCitiesWhereTradersWork() {
        return transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<Transaction> findTransactionsByYear() {
         return transactions.stream() // 스트림 생성
                 .filter(transaction -> transaction.getYear() >= 2011) //데이터 추출
                 .sorted((o1, o2) -> { //데이터 정렬 (정렬 대상 값을 Function 이용 전달)
                    if (o1.getValue() > o2.getValue()) return 1;
                    else if(o1.getValue() < o2.getValue()) return -1;
                    else return 0;
                })
                .collect(Collectors.toList()); //컬렉션 리턴
    }

    private static <T, U extends Comparable<? super U>> Comparator<T> comparing(
            Function<? super T, ? extends U> keyExtractor) // Function 을 인수로 받는다.
    {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable) //기호 &는 비트 연산자 AND를 나타냅니다.
                (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2)); //전달된 Function을 적용한다. 궁금한것은 c1, c2는 어디서 오는 값일까?
    }

}

package chapter5;

import javax.crypto.spec.PSource;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PuttingIntoPractice {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2010, 400),
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

        // 질의 1: 2011년부터 발생한 모든 거래를 찾아 값으로 정렬(작은 값에서 큰 값)
        List<Transaction> tr2011 = transactions.stream().filter(transaction -> transaction.getYear() >= 2011)
                .sorted((o1, o2) -> {
                    if (o1.getValue() > o2.getValue()) return 1;
                    else if(o1.getValue() < o2.getValue()) return -1;
                    else return 0;
                })
                .collect(Collectors.toList());
        System.out.println(tr2011);

        // 질의 2: 거래자가 근무하는 모든 고유 도시는?
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        // 질의 3: Cambridge의 모든 거래자를 찾아 이름으로 정렬.
        List<Trader>  traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(traders);

        // 질의 4: 알파벳 순으로 정렬된 모든 거래자의 이름 문자열을 반환
        List<String> names = transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                }).collect(Collectors.toList());
        System.out.println(names);

        // 질의 5: Milan에 거주하는 거래자가 있는가?
        boolean milanBased = transactions.stream().anyMatch(new Predicate<Transaction>() {
            @Override
            public boolean test(Transaction transaction) {
                return transaction.getTrader().getCity().equals("Milan");
            }
        });
        System.out.println(milanBased);

        // 질의 6: Cambridge에 사는 거래자의 모든 거래내역 출력.
        transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .collect(Collectors.toList()).forEach(System.out::println);

        // 질의 7: 모든 거래에서 최고값은 얼마인가?
        OptionalInt max = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();
        System.out.println(max.getAsInt());

        int maxValue = transactions.stream().mapToInt(Transaction::getValue)
                .reduce(0, new IntBinaryOperator() {
                    @Override
                    public int applyAsInt(int left, int right) {
                        return Integer.max(left, right);
                    }
                });
        System.out.println(maxValue);

        // 가장 작은 값을 가진 거래 탐색
        Optional<Transaction> min = transactions.stream().min(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                if(o1.getValue() > o2.getValue()) return 1;
                else if (o1.getValue() < o2.getValue()) return -1;
                else return 0;
            }
        });
        min.ifPresent(System.out::println);

        transactions.stream()
                .min(comparing(Transaction::getValue))
                .ifPresent(System.out::println);


    }

    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
            Function<? super T, ? extends U> keyExtractor) // Function 을 인수로 받는다.
    {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable) //기호 &는 비트 연산자 AND를 나타냅니다.
                (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2)); //전달된 Function을 적용한다. 궁금한것은 c1, c2는 어디서 오는 값일까?
    }

}

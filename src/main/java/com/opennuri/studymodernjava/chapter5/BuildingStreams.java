package chapter5;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStreams {
    public static void main(String[] args) {

        //스트림 만들기
        Stream<String> stringStream = Stream.of("Java 8", "Lambdas", "In", "Action");
        stringStream.map(String::toUpperCase)
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println();

        //빈스트림 만들기
        Stream<String> emptyStream = Stream.empty();

        // Arrays.stream
        int[] numbers = { 2, 3, 5, 7, 11, 13 };
        System.out.println(Arrays.stream(numbers).sum());

        // Stream.iterate
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(i-> System.out.printf("%d ", i ));
        System.out.println();


        // iterate를 이용한 피보나치 수열
        System.out.println(Stream.iterate(1, n -> n + 1)
                .limit(10000)
                .parallel()
                .reduce(0, (a, b) -> a + b)
                .longValue());
        System.out.println();

        Stream.iterate(new long[] {0, 1}, t -> new long[] {t[1], t[0] + t[1]})
                .limit(10)
                .forEach(t-> System.out.printf("(%d, %d) ", t[0], t[1] ));
        System.out.println();

        Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] })
                .limit(10)
                .map(t -> t[0])
                .forEach(i-> System.out.printf("%d ", i));

        // Stream.generate를 이용한 임의의 double 스트림
        Stream.generate(Math::random)
                .limit(10)
                .forEach(l -> System.out.println(Double.valueOf(Math.floor(l * 1000)).intValue()));
        System.out.println();

        // Stream.generate을 이용한 요소 1을 갖는 스트림
        IntStream intStream = IntStream.generate(() -> 1)
                .limit(5);
        intStream.forEach(System.out::print);
    }
}

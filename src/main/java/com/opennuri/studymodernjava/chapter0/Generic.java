package chapter0;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Generic {
   /*
    <T>	Type
    <E>	Element
    <K>	Key
    <V>	Value
    <N>	Number
    대중적으로 통하는 통상적인 선언이 가장 편하기 때문에 위와같은 암묵적(?)인 규칙
    */
   public static void main(String[] args) {

        //이 때 주의해야 할 점은 타입 파라미터로 명시할 수 있는 것은 참조 타입(Reference Type)밖에 올 수 없다.
        //즉, int, double, char 같은 primitive type은 올 수 없다는 것이다.
        // 그래서 int형 double형 등 primitive Type의 경우 Integer, Double 같은 Wrapper Type으로 쓰는 이유가 바로 위와같은 이유다.

        //ClassName이란 객체를 생성할 때 <> 안에 타입 파라미터(Type parameter)를 지정한다.
        //그러면 a객체의 ClassName의 E 제네릭 타입은 String으로 모두 변환된다.
        //반대로 b객체의 ClassName의 E 제네릭 타입은 Integer으로 모두 변환된다.
        ClassName<String> a = new ClassName<>();
        ClassName<Integer> b = new ClassName<>();

        a.set("10");
        b.set(10);
        System.out.println("a data : " + a.get());
        System.out.println("a E type : " + a.get().getClass().getName());

        System.out.println("b data : " + b.get());
        System.out.println("b E type : " + b.get().getClass().getName());

        //genericMethod()는 파라미터 타입에 따라 T 타입이 결정된다
       System.out.println("<T> returnType : " + a.genericMethod(3).getClass().getName());
       System.out.println("<T> returnType : " + a.genericMethod("ABCD").getClass().getName());
       System.out.println("<T> returnType : " + a.genericMethod(b).getClass().getName());

       System.out.println("<E> returnType : " + ClassName.genericMethod1(3).getClass().getName());
       System.out.println("<E> returnType : " + ClassName.genericMethod1("ABCD").getClass().getName());
       System.out.println("<T> returnType : " + ClassName.genericMethod2(a).getClass().getName());
       System.out.println("<T> returnType : " + ClassName.genericMethod2(3.0).getClass().getName());


        //이렇게 외부 클래스에서 제네릭 클래스를 생성할 때 <> 괄호 안에 타입을 파라미터로 보내 제네릭 타입을 지정해주는 것.
       // 이것이 바로 제네릭 프로그래밍이다.
        ClassNameTwo<String, Integer> a1 = new ClassNameTwo<>();
        a1.set("10", 10);
       System.out.println(" first data : " + a1.getFirst());
       System.out.println(" K type : " + a1.getFirst().getClass().getName());

       System.out.println(" second data : " + a1.getSecond());
       System.out.println(" V type : " + a1.getSecond().getClass().getName());


       /* <K extends T>	// T와 T의 자손 타입만 가능 (K는 들어오는 타입으로 지정 됨)
        * <K super T>	// T와 T의 부모(조상) 타입만 가능 (K는 들어오는 타입으로 지정 됨)
        * <? extends T>	// T와 T의 자손 타입만 가능
        * <? super T>	// T와 T의 부모(조상) 타입만 가능
        * <?>		// 모든 타입 가능. <? extends Object>랑 같은 의미
        * */

       //public class ClassName <K extends Number> { ... }
       //특정 타입 및 그 하위 타입만 제한 하고 싶을 경우 쓰면 된다. 좀 더 구체적으로 예로 들자면, 다음과 같다.
       // Integer는 Number 클래스를 상속받는 클래스라 가능하지만,
       // String은 Number클래스와는 완전 별개의 클래스이기 때문에 에러(Bound mismatch)를 띄운다.

       ClassNameThree<Integer> b1 = new ClassNameThree<>();
       b1.setNum(3);
       System.out.println(b1);

       ClassNameThree<Long> b2 = new ClassNameThree<>();
       b2.setNum(3L);
       System.out.println(b2);

       ClassNameThree<Double> b3 = new ClassNameThree<>();
       b3.setNum(3.0);
       System.out.println(b3);

       //Type parameter 'java.lang.String' is not within its bound; should extend 'java.lang.Number'
       //ClassNameThree<String> b4 = new ClassNameThree<>();


        //이 것은 T 타입의 부모(조상) 타입만 가능하다는 의미다. 즉, 다음과 같은 경우들이 있다.
       /*
        <K super B>	// B와 A타입만 올 수 있음
        <K super E>	// E, D, A타입만 올 수 있음
        <K super A>	// A타입만 올 수 있음

        <? super B>	// B와 A타입만 올 수 있음
        <? super E>	// E, D, A타입만 올 수 있음
        <? super A>	// A타입만 올 수 있음
        */
       /*
        * 대표적으로는 해당 객체가 업캐스팅(Up Casting)이 될 필요가 있을 때 사용한다.
       예로들어 '과일'이라는 클래스가 있고 이 클래스를 각각 상속받는 '사과'클래스와 '딸기'클래스가 있다고 가정해보자.
       이 때 각각의 사과와 딸기는 종류가 다르지만, 둘 다 '과일'로 보고 자료를 조작해야 할 수도 있다. (예로들면 과일 목록을 뽑는다거나 등등..)
       그럴 때 '사과'를 '과일'로 캐스팅 해야 하는데, 과일이 상위 타입이므로 업캐스팅을 해야한다.
       이럴 때 쓸 수 있는 것이 바로 super라는 것이다.
        */

       SaltClass<Student> c1 = new SaltClass<>();
        //매개변수화된 클래스 'LinkedList'의 원시 사용
       List list = new LinkedList();
       list.add(new Integer(1));
       Integer i = (Integer)list.iterator().next();
       System.out.println(i);

       /*
        *By adding the diamond operator <> containing the type,
        * we narrow the specialization of this list to only Integer type.
        *  In other words, we specify the type held inside the list.
        * The compiler can enforce the type at compile time.
        */
       List<Integer> listOne = new LinkedList<>();

       Generic generic = new Generic();
       Integer[] intArray = {1,2,3,4,5};
       List<Integer> integers = generic.fromArrayToList(intArray);
       System.out.println(integers);

   }

    public <T> List<T> fromArrayToList(T[] a) {
       return Arrays.stream(a).collect(Collectors.toList());
    }

    public static <T, G> List<G> fromArrayToList(T[] a, Function<T, G> mapperFunction) {
       return Arrays.stream(a).map(mapperFunction)
               .collect(Collectors.toList());
    }
}


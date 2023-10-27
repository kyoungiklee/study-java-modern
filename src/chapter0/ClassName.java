package chapter0;


 // 제네릭은 유형을 외부에서 지정해준다고 했다. 즉 해당 클래스 객체가 인스턴스화 했을 때,
 // 쉽게 말해 new 생성자로 클래스 객체를 생성하고 <> 괄호 사이에 파라미터로 넘겨준 타입으로 지정이 된다는 뜻이다.
public class ClassName<E> {
    private E element;

    public E get() {
        return element;
    }

    public void set(E element) {
        this.element = element;
    }

    // genericMethod()는 파라미터 타입에 따라 T 타입이 결정된다
    // 즉, 클래스에서 지정한 제네릭유형과 별도로 메소드에서 독립적으로 제네릭 유형을 선언하여 쓸 수 있다.\
    // static 은 무엇인가? 정적이라는 뜻이다.
    // static 변수, static 함수 등 static이 붙은 것들은 기본적으로 프로그램 실행시 메모리에 이미 올라가있다.
    // 그렇기 때문에 제네릭이 사용되는 메소드를 정적메소드로 두고 싶은 경우 제네릭 클래스와 별도로 독립적인 제네릭이 사용되어야 한다는 것이다.
    <T> T genericMethod(T o) {
        return o;
    }
    static <E> E genericMethod1(E o) {
        return o;
    }

    static <T> T genericMethod2(T o) {
        return o;
    }
}

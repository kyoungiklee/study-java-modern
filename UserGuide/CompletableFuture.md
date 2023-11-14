## CompletableFuture에 대한 이해
### Future의 단점 및 한계
java5에 추가된 Future에는 다음과 같은 한계점이 있다. 
 * 외부에서 완료 시킬 수 없고, get()의 타임아웃 설정으로만 완료 가능
 * 블로킹코드(get)을 통해서만 이후의 결과를 처리할 수 있음
 * 여러 Future를 조합할 수 없음
 * 여러 작업을 조합하거나 예외 처리 할 수 없음
Future는 외부에서 작업을 완료 시킬 수 없고 작업 완료는 오직 get호출 시에 타임아웃으로만 가능하다. 또한 비동기 작업의 응답에 추가 작업을 하려면 
get을 호충해야 하는데 get은 블로킹 호출이므로 성능에 좋지 않다. 또한 여러 Future들을 조합할 수도 없으며 예외가 발행한 경우 이를 위한 예외 처리도
불가능하다. java8에서는 이러한 문제점을 해결한 CompletableFuture가 등장한다.

### CompletableFuture 클래스
CompletableFuture는 기존 Future를 기반으로 외부에서 완료시킬 수 있어 CompletableFuture라는 이름을 갖게 되었다. Future 외에도 CompletableStage
인터페이스를 구현하는데 CompletableStage는 작업들을 중첩시키거나 완료 후 콜백을 위해 추가되었다. 예를 들어 Future에서는 불가능했던 "몇 초 이내에 
응답이 않오면 기번값을 반환한다."와 같은 작업이 가능하다. CompletableFuture는 외부에서 작업을 완료 시킬 수 있고 콜백 등록 및 Future 조합이 가능하다.

## CompletetableFuture의 기능 및 예시코드
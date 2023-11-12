# studyModernJava

## gradle 프로젝트 오픈시 build.gradle.kts, settings.gradle.kts 빨간줄이 보이는 경우
File > Invalidate Cachees... 로 캐시를 삭제하면 정상적으로 보임

## chapter4
안녕하세요 chapter4에 대해 상세 업무내용을 정리합니다.

## chapter6

## Grouping

#### Collections.groupingBy() 메소드를 사용하여 그룹핑할 수 있다.
```
public static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(groupingBy(Dish::getType));
    }
```
####  groupingBy() 메소드에 mapping() 을 이용하여 원하는 값으로 변경할 수 있다. 

```
private static Map<Dish.Type, List<String>>  groupDishNamesByType() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType, //classifier
                        mapping(Dish::getName, toList()) //mapping
                ));
    }
```

#### groupingBy() 메소드에 두번재 인수로 flatMapping()을 이용하여 또 다른 스트림을 사용할 수 있다.
```
private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())
                ));
    }
```

#### groupingBy() 메소드에 두번재 인수로 filtering을 이용하여 결과값을 필터할 수 있다
```
private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {

        return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        filtering(dish -> dish.getCalories() > 500, toList())
                ));
    }

```

#### groupingBy() 메소드에 분류값을 적용할 수 있다.
```
private static Map<Dish.CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {

        return menu.stream()
                .collect(groupingBy(dish -> {
                            if (dish.getCalories() <= 300) return Dish.CaloricLevel.DIET;
                            else if (dish.getCalories() <= 500) return Dish.CaloricLevel.NORMAL;
                            else return Dish.CaloricLevel.FAT;
                        }
                ));
    }
```

#### groupingBy() 메소드에 두번재 인수로 또 다른 groupingBy() 메소드를 추가할 수 있다.
```
private static Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {

        return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        groupingBy(dish -> {
                    if (dish.getCalories() <= 300) return Dish.CaloricLevel.DIET;
                    else if (dish.getCalories() <= 500 ) return Dish.CaloricLevel.NORMAL;
                    else return Dish.CaloricLevel.FAT;
                }, toList())
                ));
    }
```

#### groupingBy() 메소드에 두번재 인수로 counting()을 사용하여 그룹의 갯수를 확인할 수 있다.
```
private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream()
                .collect(groupingBy(
                    Dish::getType, 
                    counting()
                ));
    }
```

#### groupingBy() 메소드에 두번재 인수로 maxBy()를 이용하여 최대값을 찾을 수 있다.

```
private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByTypeFirst() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        maxBy(comparingInt(Dish::getCalories))
                ));
    }
```

#### groupingBy() 메소드에 두번재 인수로 reducing() 이용하여 최대값을 찾을 수 있다. 
```
private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByTypeSecond() {
       return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)
                ));
    }
```

#### groupingBy() 메소드에 두번재 인수로 collectingAndThen()이옹햐여 값을 확인할 수 있다.
```
private static  Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOptionals() {
       return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        collectingAndThen(
                            reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2),
                            Optional::get
                        )
                ));
    }
```

#### groupingBy() 메소드에 두번재 인수로 summingInt()를 이용하여 합계를 구할 수 있다.
```
private static Map<Dish.Type, Integer> sumCaloriesByType() {
         return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        summingInt(Dish::getCalories)
                ));
    }
```


#### groupingBy() 메소드에 두번재 인수로 mapping()을 이용하여 다른 값으로 처리할 수 있다.
```
private static Map<Dish.Type, List<Dish.CaloricLevel>> caloricLevelsByType() {
        return menu.stream()
                .collect(groupingBy(Dish::getType, 
                        mapping((Dish dish) -> {
                            if (dish.getCalories() < 300) return Dish.CaloricLevel.DIET;
                            else if (dish.getCalories() < 500) return Dish.CaloricLevel.NORMAL;
                            return Dish.CaloricLevel.FAT;
                        }, toList())
                ));
    }
```

#### groupingBy() 메소드 와 sorted() 메소드를 사용하여 그룹내 정렬을 할 수 있다. 
```
private static Map<Dish.Type, List<Dish>> groupByTypeFirstSorted() {
        return menu
                .stream()
                .sorted(comparing(Dish::getName))
                .collect(Collectors.groupingBy(Dish::getType));
    }
```

#### groupingBy() 메소드로 먼저 그룹핑 후 정렬을 수행할 수 있다. 
```
private static Map<Dish.Type, List<Dish>> groupByTypeFirstGrouping() {
        Map<Dish.Type, List<Dish>> collect = menu.stream()
                .collect(groupingBy(Dish::getType));

        collect.values()
                .forEach(list -> list.sort(comparing(Dish::getName)));

        return collect;

    }
```
### 전체 코드
```java
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Grouping {

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

    public static final Map<String, List<String>> dishTags = new HashMap<>();
    static {
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));
    }
    public static void main(String[] args) {
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dish names grouped by type: " + groupDishNamesByType());
        System.out.println("Dish tags grouped by type: " + groupDishTagsByType());
        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type first way: " + mostCaloricDishesByTypeFirst());
        System.out.println("Most caloric dishes by type second way: " + mostCaloricDishesByTypeSecond());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOptionals());
        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
        System.out.println("Sorted dishes name grouped by type: " + groupByTypeFirstSorted());
        System.out.println("Sorted dishes name grouped by type: " + groupByTypeFirstGrouping());

    }

    private static Map<Dish.Type, List<Dish>> groupByTypeFirstGrouping() {
        Map<Dish.Type, List<Dish>> collect = menu.stream()
                .collect(groupingBy(Dish::getType));

        collect.values()
                .forEach(list -> list.sort(comparing(Dish::getName)));

        return collect;

    }

    private static Map<Dish.Type, List<Dish>> groupByTypeFirstSorted() {
        return menu
                .stream()
                .sorted(comparing(Dish::getName))
                .collect(Collectors.groupingBy(Dish::getType));
    }

    private static Map<Dish.Type, List<Dish.CaloricLevel>> caloricLevelsByType() {
        return menu.stream()
                .collect(groupingBy(Dish::getType, 
                        mapping((Dish dish) -> {
                            if (dish.getCalories() < 300) return Dish.CaloricLevel.DIET;
                            else if (dish.getCalories() < 500) return Dish.CaloricLevel.NORMAL;
                            return Dish.CaloricLevel.FAT;
                        }, toList())
                ));
    }

    private static Map<Dish.Type, Integer> sumCaloriesByType() {
         return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        summingInt(Dish::getCalories)
                ));
    }

    private static  Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOptionals() {
       return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        collectingAndThen(
                            reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2),
                            Optional::get
                        )
                ));
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByTypeSecond() {
       return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)
                ));
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByTypeFirst() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        maxBy(comparingInt(Dish::getCalories))
                ));
    }

    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream()
                .collect(groupingBy(
                    Dish::getType, 
                    counting()
                ));
    }


    private static Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {

        return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        groupingBy(dish -> {
                    if (dish.getCalories() <= 300) return Dish.CaloricLevel.DIET;
                    else if (dish.getCalories() <= 500 ) return Dish.CaloricLevel.NORMAL;
                    else return Dish.CaloricLevel.FAT;
                }, toList())
                ));
    }

    private static Map<Dish.CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {

        return menu.stream()
                .collect(groupingBy(
                        dish -> {
                            if (dish.getCalories() <= 300) return Dish.CaloricLevel.DIET;
                            else if (dish.getCalories() <= 500) return Dish.CaloricLevel.NORMAL;
                            else return Dish.CaloricLevel.FAT;
                        }
                ));
    }

    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {

        return menu.stream()
                .collect(groupingBy(
                        Dish::getType, 
                        filtering(dish -> dish.getCalories() > 500, toList())
                ));
    }

    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())
                ));
    }

    private static Map<Dish.Type, List<String>>  groupDishNamesByType() {
        return menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        mapping(Dish::getName, toList())
                ));
    }

    public static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(groupingByConcurrent(Dish::getType));
    }
}
```

## reducing

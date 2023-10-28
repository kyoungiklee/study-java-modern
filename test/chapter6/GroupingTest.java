package chapter6;

import chapter04.Dish;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class GroupingTest {

    public static final List<Dish> otherMenu = Arrays.asList(
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER)
    );

    @Test
    void groupDishesByTypeTest() {
        Map<Dish.Type, List<Dish>> typeListMap = Grouping.groupDishesByType();
    }
}
package chapter6;

import chapter04.Dish;
import chapter04.Type;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static chapter04.Type.OTHER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GroupingTest {

    public static final List<Dish> otherMenu = Arrays.asList(
            new Dish("french fries", true, 530, Type.OTHER),
            new Dish("rice", true, 350, Type.OTHER),
            new Dish("season fruit", true, 120, Type.OTHER),
            new Dish("pizza", true, 550, Type.OTHER)
    );

    @Test
    void groupDishesByTypeTest() {
        Map<Type, List<Dish>> typeListMap = Grouping.groupDishesByType();
    }
}
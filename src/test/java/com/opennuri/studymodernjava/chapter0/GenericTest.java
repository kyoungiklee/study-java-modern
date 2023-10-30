package com.opennuri.studymodernjava.chapter0;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Objects;


class GenericTest {
    @Test
    public void givenArrayOfIntegers_thanListOfStringReturnedOK() {
        Integer[] intArray = {1,2,3,4,5};
        List<String> strings = Generic.fromArrayToList(intArray, Objects::toString);
        assertThat(strings).contains("1","2","3","4","5");
    }


}
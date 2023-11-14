package com.opennuri.studymodernjava.chapter16.domain;

import lombok.Getter;

import java.util.Random;

import static com.opennuri.studymodernjava.chapter16.domain.Util.delay;
import static com.opennuri.studymodernjava.chapter16.domain.Util.format;

public class Shop {
    @Getter
    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random((long) name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return name + ":" + price + ":" + code;
    }

    private double calculatePrice(String product) {
        delay();
        return format(random.nextDouble() * product.charAt(0) * product.charAt(1));
    }
}

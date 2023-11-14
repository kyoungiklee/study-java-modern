package com.opennuri.studymodernjava.chapter16.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;

import static com.opennuri.studymodernjava.chapter16.domain.Util.delay;
import static com.opennuri.studymodernjava.chapter16.domain.Util.format;

public class Discount {

    @RequiredArgsConstructor
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentige;
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }


    private static double apply(double price, Code discountCode) {
        delay();  //각 shop오로 부터 견적을 받는데 걸리는 시간 모델링
        return format(price * (100 - discountCode.percentige) / 100) ;
    }
}

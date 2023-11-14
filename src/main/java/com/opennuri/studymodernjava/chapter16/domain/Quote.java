package com.opennuri.studymodernjava.chapter16.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Quote {
    private final String shopName;
    private final double price;
    private final Discount.Code discountCode;

    public static Quote parse (String shopNameWithPriceAndCode) {
        String[] split = shopNameWithPriceAndCode.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discountCode = Discount.Code.valueOf(split[2]);
        return new Quote(shopName,price, discountCode);
    }

}

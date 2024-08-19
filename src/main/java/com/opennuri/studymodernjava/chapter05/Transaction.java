package com.opennuri.studymodernjava.chapter05;

import java.util.Objects;

public class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (trader == null ? 0 : trader.hashCode());
        hash = hash * 31 + year;
        hash = hash * 31 + value;
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof Transaction)) return false;
        Transaction o = (Transaction) other;
        boolean eq = Objects.equals(trader, ((Transaction) other).getTrader());
        eq = eq && Objects.equals(year, o.getYear());
        eq = eq && Objects.equals(value, o.getValue());
        return eq;
    }

    @Override
    public String toString() {
        return String.format("%s, year: %d, value: %d", trader, year, value);
    }
}

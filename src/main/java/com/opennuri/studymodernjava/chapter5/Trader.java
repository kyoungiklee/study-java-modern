package com.opennuri.studymodernjava.chapter5;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
@AllArgsConstructor
public class Trader {
    private String name;
    private String city;

    @Override
    public int hashCode() {
        int hash =17;
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (city == null ? 0 : city.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        if(!(other instanceof Trader trader)) return false;
        boolean eq = Objects.equals(name, trader.getName());
        eq = eq && Objects.equals(city, trader.getCity());
        return eq;
    }

    @Override
    public String toString() {

        return String.format("Trader:%s in %s", name, city);
    }
}

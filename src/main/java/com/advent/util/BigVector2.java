package com.advent.util;

public record BigVector2(long x, long y) {

    public BigVector2 add(BigVector2 vector) {
        return new BigVector2(x + vector.x, y + vector.y);
    }

    public BigVector2 minus(BigVector2 vector) {
        return new BigVector2(x - vector.x, y - vector.y);
    }
}

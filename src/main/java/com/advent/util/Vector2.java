package com.advent.util;

public record Vector2(int x, int y) {

    public Vector2 add(Vector2 vector) {
        return new Vector2(x + vector.x, y + vector.y);
    }

    public Vector2 minus(Vector2 vector) {
        return new Vector2(x - vector.x, y - vector.y);
    }
}

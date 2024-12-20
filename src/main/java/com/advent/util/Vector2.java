package com.advent.util;

public record Vector2(int x, int y) {

    public Vector2 add(Vector2 vector) {
        return new Vector2(x + vector.x, y + vector.y);
    }

    public Vector2 subtract(Vector2 vector) {
        return new Vector2(x - vector.x, y - vector.y);
    }

    public Vector2 add(Direction direction) {
        return new Vector2(x + direction.vector().x, y + direction.vector().y);
    }

    public int distance(Vector2 other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}

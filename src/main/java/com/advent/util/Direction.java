package com.advent.util;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Direction {
    NORTH(new Vector2(0, -1)),
    EAST(new Vector2(1, 0)),
    SOUTH(new Vector2(0, 1)),
    WEST(new Vector2(-1, 0));

    private final Vector2 vector;

    Direction(Vector2 vector2) {
        this.vector = vector2;
    }

    public Vector2 vector() {
        return vector;
    }

    public Direction turnRight() {
        return switch (this) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case EAST -> SOUTH;
            case WEST -> NORTH;
        };
    }

    public static Stream<Direction> stream() {
        return Arrays.stream(values());
    }
}

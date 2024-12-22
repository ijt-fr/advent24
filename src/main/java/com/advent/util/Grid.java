package com.advent.util;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid<T> {

    private final T[][] grid;

    public T[][] grid() {
        return grid;
    }

    public int maxX() {
        return maxX;
    }

    public int maxY() {
        return maxY;
    }

    private final int maxX;
    private final int maxY;

    private Grid(T[][] grid) {
        this.grid = grid;
        this.maxX = grid[0].length;
        this.maxY = grid.length;
    }

    public static Grid<Character> ofChars(Character[][] grid) {
        return new Grid<>(grid);
    }

    public static Grid<Integer> ofInts(Integer[][] grid) {
        return new Grid<>(grid);
    }


    public boolean outOfBounds(Vector2 newPosition) {
        return newPosition.x() == maxX || newPosition.y() == maxY || newPosition.x() < 0 || newPosition.y() < 0;
    }

    public T get(int x, int y) {
        return grid[y][x];
    }

    public T get(Vector2 vector) {
        return grid[vector.y()][vector.x()];
    }

    public Stream<Cell<T>> stream() {
        return IntStream.range(0, maxX)
                       .boxed().flatMap(i -> IntStream.range(0, maxY)
                                             .mapToObj(j -> new Cell<T>(new Vector2(i, j), get(i, j))));
    }

    public void put(Vector2 vector, T c) {
        grid[vector.y()][vector.x()] = c;
    }

    public Optional<Vector2> anyVectorOf(T t) {
        return stream().filter(c -> c.entry().equals(t)).map(Cell::vector).findAny();
    }

    public record Cell<T>(Vector2 vector, T entry) {

    }
}

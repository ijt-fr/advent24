package com.advent.day15.obstacle;

import java.util.List;

import com.advent.util.Vector2;

public class Obstacles {

    private Obstacles() {
        // private constructor
    }


    public static Obstacle wall(Vector2 location) {
        return new Wall(List.of(location));
    }

    public static Obstacle box(Vector2 location) {
        return new Box(List.of(location));
    }

    public static Obstacle bigBox(Vector2 location1, Vector2 location2) {
        return new Box(List.of(location1, location2));
    }
}

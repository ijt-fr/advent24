package com.advent.day15.obstacle;

import java.util.Objects;
import java.util.Set;

import com.advent.util.Direction;
import com.advent.util.Vector2;

public abstract class Obstacle {
    private final Set<Vector2> locations;

    Obstacle(Set<Vector2> locations) {
        this.locations = locations;
    }

    public abstract int gps();

    public static Obstacle wall(Vector2 location) {
        return new Wall(Set.of(location));
    }

    public static Obstacle box(Vector2 location) {
        return new Box(Set.of(location));
    }

    public static Obstacle bigBox(Vector2 location1, Vector2 location2) {
        return new BigBox(Set.of(location1, location2));
    }

    public Set<Vector2> locations() {
        return locations;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Obstacle) obj;
        return Objects.equals(this.locations, that.locations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locations);
    }

    public abstract boolean move(Direction direction, Set<Obstacle> obstacles);
}

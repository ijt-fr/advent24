package com.advent.day15.obstacle;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.advent.util.Direction;
import com.advent.util.Vector2;

public abstract class Obstacle {
    private final List<Vector2> locations;

    Obstacle(List<Vector2> locations) {
        this.locations = locations;
    }

    public abstract int gps();

    public List<Vector2> locations() {
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

    public boolean move(Direction direction, Set<Obstacle> obstacles) {
        boolean canMove = canMove(direction, obstacles);
        if (canMove) {
            doMove(direction, obstacles);
        }
        return canMove;
    }

    protected abstract boolean canMove(Direction direction, Set<Obstacle> obstacles);

    protected abstract void doMove(Direction direction, Set<Obstacle> obstacles);
}

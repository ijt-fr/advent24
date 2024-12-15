package com.advent.day15.obstacle;

import java.util.List;
import java.util.Set;

import com.advent.util.Direction;
import com.advent.util.Vector2;

final class Wall extends Obstacle {

    Wall(List<Vector2> locations) {
        super(locations);
    }

    @Override
    public int gps() {
        return 0;
    }

    @Override
    protected void doMove(Direction direction, Set<Obstacle> obstacles) {
        throw new IllegalStateException("Wall cannot move");
    }

    @Override
    protected boolean canMove(Direction direction, Set<Obstacle> obstacles) {
        return false;
    }
}

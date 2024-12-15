package com.advent.day15.obstacle;

import java.util.Set;

import com.advent.util.Direction;
import com.advent.util.Vector2;

public final class Wall extends Obstacle {

    Wall(Set<Vector2> locations) {
        super(locations);
    }

    @Override
    public int gps() {
        return 0;
    }

    @Override
    public boolean move(Direction direction, Set<Obstacle> obstacles) {
        return false;
    }
}

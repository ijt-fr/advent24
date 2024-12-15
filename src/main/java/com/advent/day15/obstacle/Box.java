package com.advent.day15.obstacle;

import java.util.Optional;
import java.util.Set;

import com.advent.util.Direction;
import com.advent.util.Vector2;

public final class Box extends Obstacle {

    private final Vector2 location;

    Box(Set<Vector2> locations) {
        super(locations);
        location = locations.stream().findAny().orElseThrow();
    }

    @Override
    public int gps() {
        return location.x() + (100 * location.y());
    }

    @Override
    public boolean move(Direction direction, Set<Obstacle> obstacles) {
        Vector2 destination = location.add(direction);
        Optional<Obstacle> obstacle = obstacles.stream()
                                              .filter(ob -> ob.locations().contains(destination))
                                              .findAny();
        if (obstacle.isEmpty()) {
            obstacles.remove(this);
            obstacles.add(Obstacle.box(destination));
            return true;
        }
        if (obstacle.get().move(direction, obstacles)) {
            obstacles.remove(this);
            obstacles.add(Obstacle.box(destination));
            return true;
        }
        return false;
    }
}

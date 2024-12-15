package com.advent.day15.obstacle;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.advent.util.Direction;
import com.advent.util.Vector2;

public final class Box extends Obstacle {

    Box(List<Vector2> locations) {
        super(locations);
    }

    @Override
    public int gps() {
        return locations().getFirst().x() + (100 * locations().getFirst().y());
    }

    @Override
    protected void doMove(Direction direction, Set<Obstacle> obstacles) {
        Vector2 destination = locations().getFirst().add(direction);
        Optional<Obstacle> obstacle = obstacles.stream()
                                              .filter(ob -> ob.locations().contains(destination))
                                              .findAny();

        obstacle.ifPresent(value -> value.move(direction, obstacles));
        obstacles.remove(this);
        obstacles.add(Obstacles.box(destination));
    }

    @Override
    protected boolean canMove(Direction direction, Set<Obstacle> obstacles) {
        Vector2 destination = locations().getFirst().add(direction);
        Optional<Obstacle> obstacle = obstacles.stream()
                                              .filter(ob -> ob.locations().contains(destination))
                                              .findAny();
        return obstacle.map(value -> value.canMove(direction, obstacles)).orElse(true);
    }
}

package com.advent.day15.obstacle;

import java.util.List;
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
        locations().stream()
                .map(location -> {
                    Vector2 dest = location;
                    while (locations().contains(dest)) {
                        dest = dest.add(direction);
                    }
                    return dest;
                })
                .map(destination -> obstacles.stream()
                                            .filter(ob -> ob.locations().contains(destination))
                                            .findAny())
                        .forEach(obstacle -> {
                            obstacle.ifPresent(value -> value.move(direction, obstacles));
                        });
        obstacles.remove(this);
        obstacles.add(Obstacles.bigBox(locations().getFirst().add(direction), locations().getLast().add(direction)));
    }

    @Override
    protected boolean canMove(Direction direction, Set<Obstacle> obstacles) {
        return locations().stream()
                .map(location -> {
                    Vector2 dest = location;
                    while (locations().contains(dest)) {
                        dest = dest.add(direction);
                    }
                    return dest;
                })
                .map(destination -> obstacles.stream()
                                            .filter(ob -> ob.locations().contains(destination))
                                            .findAny())
                .map(obstacle -> obstacle
                                         .map(value -> value.canMove(direction, obstacles))
                                         .orElse(true))
                .reduce( (a, b) -> a && b).orElse(true);
    }
}

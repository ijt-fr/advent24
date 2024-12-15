package com.advent.day15.obstacle;

import java.util.Optional;
import java.util.Set;

import com.advent.util.Direction;
import com.advent.util.Vector2;

public final class BigBox extends Obstacle {

    BigBox(Set<Vector2> locations) {
        super(locations);
    }

    @Override
    public int gps() {
        return 0;
    }

    @Override
    public boolean move(Direction direction, Set<Obstacle> obstacles) {
        locations().stream().map(location -> {
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
        });
        return false;
    }
}

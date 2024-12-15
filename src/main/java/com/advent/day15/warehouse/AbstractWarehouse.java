package com.advent.day15.warehouse;

import java.util.Optional;
import java.util.Set;

import com.advent.day15.obstacle.Obstacle;
import com.advent.util.Direction;
import com.advent.util.Vector2;

public abstract class AbstractWarehouse {

    protected Vector2 robot;

    public void moveBot(Direction direction) {
        Vector2 destination = robot.add(direction);
        Optional<Obstacle> obstacle = obstacles().stream()
                                              .filter(ob -> ob.locations().contains(destination))
                                              .findAny();
        if (obstacle.map(value -> value.move(direction, obstacles())).orElse(true)) {
            robot = destination;
        }
    }

    abstract Set<Obstacle> obstacles();
}

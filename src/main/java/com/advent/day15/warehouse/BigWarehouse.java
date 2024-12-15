package com.advent.day15.warehouse;

import java.util.HashSet;
import java.util.Set;

import com.advent.day15.obstacle.Obstacle;
import com.advent.day15.obstacle.Obstacles;
import com.advent.util.Grid;
import com.advent.util.Vector2;

public class BigWarehouse extends AbstractWarehouse {

    private final Set<Obstacle> obstacles;

    public BigWarehouse(Grid<Character> grid) {
        obstacles = new HashSet<>();
        for (int x = 0; x < grid.maxX(); x++) {
            for (int y = 0; y < grid.maxY(); y++) {
                switch (grid.get(x, y)) {
                    case 'O' -> obstacles.add(Obstacles.bigBox(new Vector2((x * 2), y), new Vector2((x * 2) + 1, y)));
                    case '#' -> {
                        obstacles.add(Obstacles.wall(new Vector2((x * 2), y)));
                        obstacles.add(Obstacles.wall(new Vector2((x * 2) + 1, y)));
                    }
                    case '@' -> robot = new Vector2((x * 2), y);
                }
            }
        }
    }

    @Override
    public Set<Obstacle> obstacles() {
        return obstacles;
    }
}

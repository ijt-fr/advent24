package com.advent.day15.warehouse;

import java.util.HashSet;
import java.util.Set;

import com.advent.day15.obstacle.Obstacle;
import com.advent.day15.obstacle.Obstacles;
import com.advent.util.Grid;
import com.advent.util.Vector2;

public final class Warehouse extends AbstractWarehouse {

    private final Set<Obstacle> obstacles;

    public Warehouse(Grid<Character> grid) {
        obstacles = new HashSet<>();
        for (int x = 0; x < grid.maxX(); x++) {
            for (int y = 0; y < grid.maxY(); y++) {
                switch (grid.get(x, y)) {
                    case 'O' -> obstacles.add(Obstacles.box(new Vector2(x, y)));
                    case '#' -> obstacles.add(Obstacles.wall(new Vector2(x, y)));
                    case '@' -> robot = new Vector2(x, y);
                }
            }
        }
    }

    @Override
    public Set<Obstacle> obstacles() {
        return obstacles;
    }
}

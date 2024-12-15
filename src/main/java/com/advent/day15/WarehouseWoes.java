package com.advent.day15;

import static com.advent.day15.WarehouseWoes.Obstacle.BOX;
import static com.advent.day15.WarehouseWoes.Obstacle.WALL;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.advent.Puzzle;
import com.advent.util.Direction;
import com.advent.util.Grid;
import com.advent.util.InputUtils;
import com.advent.util.Vector2;

public class WarehouseWoes extends Puzzle {
    private Deque<Direction> directions;
    private Warehouse warehouse;

    @Override
    public void parseInput(List<String> lines) {
        int split = lines.indexOf("");
        Grid<Character> grid = Grid.ofChars(InputUtils.toCharMatrix(lines.subList(0, split)));
        Map<Vector2, Obstacle> obstacles = new HashMap<>();
        Vector2 robot = null;
        for (int x = 0; x < grid.maxX(); x++) {
            for (int y = 0; y < grid.maxY(); y++) {
                switch (grid.get(x, y)) {
                    case 'O' -> obstacles.put(new Vector2(x, y), BOX);
                    case '#' -> obstacles.put(new Vector2(x, y), Obstacle.WALL);
                    case '@' -> robot = new Vector2(x, y);
                }
            }
        }
        var directionsInput = String.join("", lines.subList(split + 1, lines.size())).toCharArray();
        this.directions = new ArrayDeque<>();
        for (int i = 0; i < directionsInput.length; i++) {
            var direction = directionsInput[i];
            switch (direction) {
                case '>' -> directions.push(Direction.EAST);
                case '<' -> directions.push(Direction.WEST);
                case '^' -> directions.push(Direction.NORTH);
                case 'v' -> directions.push(Direction.SOUTH);
            }
        }
        directions = directions.reversed();
        warehouse = new Warehouse(robot, obstacles);
    }

    @Override
    public Object computePart1() {
        while (!directions.isEmpty()) {
            Direction direction = directions.pop();
            warehouse.moveBot(direction);
        }
        return warehouse.obstacles().entrySet().stream()
                       .filter(entry -> BOX.equals(entry.getValue()))
                       .map(Map.Entry::getKey)
                       .map(v -> v.x() + (100 * v.y()))
                       .reduce(Integer::sum).orElse(0);
    }

    @Override
    public Object part1Answer() {
        return 1415498;
    }

    @Override
    public Object computePart2() {
        return null;
    }

    @Override
    public Object part2Answer() {
        return null;
    }

    enum Obstacle {
        BOX, WALL;
    }

    static final class Warehouse {
        private Vector2 robot;
        private final Map<Vector2, Obstacle> obstacles;

        Warehouse(Vector2 robot, Map<Vector2, Obstacle> obstacles) {
            this.robot = robot;
            this.obstacles = obstacles;
        }

        public void moveBot(Direction direction) {
            if (move(robot.add(direction), direction)) {
                robot = robot.add(direction);
            }
        }

        private boolean move(Vector2 object, Direction direction) {
            Obstacle obstacle = obstacles.get(object);
            if (WALL.equals(obstacle)) {
                return false;
            } else if (BOX.equals(obstacle)) {
                if (move(object.add(direction), direction)) {
                    obstacles.remove(object);
                    obstacles.put(object.add(direction), BOX);
                    return true;
                }
                return false;
            }
            return true;
        }

        public Map<Vector2, Obstacle> obstacles() {
            return obstacles;
        }

    }
}

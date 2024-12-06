package com.advent.day6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.advent.Puzzle;
import com.advent.util.InputUtils;
import com.advent.util.Vector2;

public class GuardGallivant extends Puzzle {

    private List<Vector2> obstacles;
    private Vector2 currentPosition;
    private Direction facing;
    private int maxX;
    private int maxY;

    @Override
    public void parseInput(List<String> lines) {
        char[][] matrix = InputUtils.toCharMatrix(lines);
        maxX = matrix[0].length;
        maxY = matrix.length;
        obstacles = new ArrayList<>();

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (matrix[j][i] == '#') {
                    obstacles.add(new Vector2(i, j));
                }
                if (matrix[j][i] == '^') {
                    currentPosition = new Vector2(i, j);
                    facing = Direction.NORTH;
                }
            }
        }
    }

    @Override
    public Object computePart1() {
        return visitAll().size();
    }

    private Set<Vector2> visitAll() {
        Set<Vector2> visited = new HashSet<>();
        visited.add(currentPosition);
        while (true) {
            var newPosition = currentPosition.add(facing.vector());
            if (outOfBounds(newPosition)) {
                break;
            }
            if (collision(newPosition)) {
                facing = facing.turnRight();
            } else {
                visited.add(newPosition);
                currentPosition = newPosition;
            }
        }
        return visited;
    }

    private boolean collision(Vector2 newPosition) {
        return obstacles.contains(newPosition);
    }

    private boolean outOfBounds(Vector2 newPosition) {
        return newPosition.x() == maxX || newPosition.y() == maxY || newPosition.x() < 0 || newPosition.y() < 0;
    }

    @Override
    public Object part1Answer() {
        return 5129;
    }

    @Override
    public Object computePart2() {
        Vector2 startPosition = currentPosition;
        var startFacing = facing;
        long loopObs = 0L;
        for (Vector2 vector2 : visitAll()) {
            if (startPosition.equals(vector2)) {
                continue;
            }
            if (isLoop(vector2, startPosition, startFacing)) {
                loopObs++;
            }
        }
        return loopObs;
    }

    private boolean isLoop(Vector2 newObstacle, Vector2 startPosition, Direction startFacing) {
        obstacles.add(newObstacle);
        currentPosition = startPosition;
        facing = startFacing;

        Map<Vector2, Set<Direction>> visited = new HashMap<>();
        addCurrentPosition(visited);
        var isLoop = false;
        while (true) {
            var newPosition = currentPosition.add(facing.vector());
            if (outOfBounds(newPosition)) {
                break;
            }
            if (collision(newPosition)) {
                facing = facing.turnRight();
            } else {
                currentPosition = newPosition;
                if (visited.containsKey(currentPosition) && visited.get(currentPosition).contains(facing)) {
                    isLoop = true;
                    break;
                }
                addCurrentPosition(visited);
            }
        }
        obstacles.remove(newObstacle);
        return isLoop;
    }

    private void addCurrentPosition(Map<Vector2, Set<Direction>> visited) {
        visited.compute(currentPosition, (k, v) -> {
            if (v == null) {
                v = new HashSet<>();
            }
            v.add(facing);
            return v;
        });
    }

    @Override
    public Object part2Answer() {
        return 1888L;
    }
}

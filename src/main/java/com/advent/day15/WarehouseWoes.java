package com.advent.day15;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.advent.Puzzle;
import com.advent.day15.obstacle.Obstacle;
import com.advent.day15.warehouse.BigWarehouse;
import com.advent.day15.warehouse.Warehouse;
import com.advent.util.Direction;
import com.advent.util.Grid;
import com.advent.util.InputUtils;

public class WarehouseWoes extends Puzzle {
    private Deque<Direction> directions;
    private Grid<Character> grid;

    @Override
    public void parseInput(List<String> lines) {
        int split = lines.indexOf("");
        grid = Grid.ofChars(InputUtils.toCharMatrix(lines.subList(0, split)));
        directions = parseDirections(String.join("", lines.subList(split + 1, lines.size())));
    }

    private Deque<Direction> parseDirections(String directionsInput) {
        Deque<Direction> directions = new ArrayDeque<>();
        for (char direction : directionsInput.toCharArray()) {
            switch (direction) {
                case '>' -> directions.push(Direction.EAST);
                case '<' -> directions.push(Direction.WEST);
                case '^' -> directions.push(Direction.NORTH);
                case 'v' -> directions.push(Direction.SOUTH);
            }
        }
        return directions.reversed();
    }

    @Override
    public Object computePart1() {
        Warehouse warehouse = new Warehouse(grid);
        while (!directions.isEmpty()) {
            Direction direction = directions.pop();
            warehouse.moveBot(direction);
        }
        return warehouse.obstacles().stream()
                       .map(Obstacle::gps)
                       .reduce(Integer::sum)
                       .orElse(0);
    }

    @Override
    public Object part1Answer() {
        return 1415498;
    }

    @Override
    public Object computePart2() {
        BigWarehouse warehouse = new BigWarehouse(grid);
        while (!directions.isEmpty()) {
            Direction direction = directions.pop();
            warehouse.moveBot(direction);
        }
        return warehouse.obstacles().stream()
                       .map(Obstacle::gps)
                       .reduce(Integer::sum)
                       .orElse(0);
    }

    @Override
    public Object part2Answer() {
        return 1432898;
    }

}

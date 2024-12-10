package com.advent.day10;

import static com.advent.day6.Direction.EAST;
import static com.advent.day6.Direction.NORTH;
import static com.advent.day6.Direction.SOUTH;
import static com.advent.day6.Direction.WEST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.advent.Puzzle;
import com.advent.day6.Direction;
import com.advent.util.Grid;
import com.advent.util.InputUtils;
import com.advent.util.Vector2;

public class HoofIt extends Puzzle {
    private Grid<Integer> map;
    private Set<Vector2> trailheads;

    @Override
    public void parseInput(List<String> lines) {
        trailheads = new HashSet<>();
        map = Grid.ofInts(InputUtils.toIntMatrix(lines));
        for (int x = 0; x < map.maxX(); x++) {
            for (int y = 0; y < map.maxY(); y++) {
                if (map.get(x, y) == 0) {
                    trailheads.add(new Vector2(x, y));
                }
            }
        }
    }

    @Override
    public Object computePart1() {
        return trailheads.stream().map(trailhead ->  Arrays.stream(Direction.values())
                                                             .flatMap(d -> findEnds(d, trailhead, 0))
                                                             .collect(Collectors.toSet()))
                       .map(Set::size)
                       .reduce(Integer::sum)
                       .orElse(0);
    }

    private Stream<Vector2> findEnds(Direction direction, Vector2 position, int currentHeight) {
        var newPosition = position.add(direction.vector());
        var newHeight = currentHeight + 1;
        if (map.outOfBounds(newPosition)) {
            return Stream.empty();
        }
        if (map.get(newPosition) == newHeight) {
            if (newHeight == 9) {
                return Stream.of(newPosition);
            }
            return Arrays.stream(Direction.values()).flatMap(d -> findEnds(d, newPosition, newHeight));
        }
        return Stream.empty();
    }

    @Override
    public Object part1Answer() {
        return 550;
    }

    @Override
    public Object computePart2() {
        return trailheads.stream()
                       .map(trailhead -> Arrays.stream(Direction.values())
                                                 .map(direction -> trailCount(direction, trailhead, 0))
                                                 .reduce(Integer::sum).orElse(0))
                       .reduce(Integer::sum).orElse(0);
    }

    private Integer trailCount(Direction direction, Vector2 position, int currentHeight) {
        var newPosition = position.add(direction.vector());
        var newHeight = currentHeight + 1;
        if (map.outOfBounds(newPosition)) {
            return 0;
        }
        if (map.get(newPosition) == newHeight) {
            if (newHeight == 9) {
                return 1;
            }
            return Arrays.stream(Direction.values())
                           .map(d -> trailCount(d, newPosition, newHeight))
                           .reduce(Integer::sum).orElse(0);
        }
        return 0;
    }

    @Override
    public Object part2Answer() {
        return 1255;
    }
}

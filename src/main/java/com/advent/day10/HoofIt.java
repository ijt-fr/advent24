package com.advent.day10;

import static com.advent.day6.Direction.EAST;
import static com.advent.day6.Direction.NORTH;
import static com.advent.day6.Direction.SOUTH;
import static com.advent.day6.Direction.WEST;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    private Map<Vector2, Set<Vector2>> trailheads;

    @Override
    public void parseInput(List<String> lines) {
        trailheads = new HashMap<>();
        map = Grid.ofInts(InputUtils.toIntMatrix(lines));
        for (int x = 0; x < map.maxX(); x++) {
            for (int y = 0; y < map.maxY(); y++) {
                if (map.get(x, y) == 0) {
                    trailheads.put(new Vector2(x, y), new HashSet<>());
                }
            }
        }
    }

    @Override
    public Object computePart1() {
        trailheads.keySet().forEach(trailhead -> {
            trailheads.put(trailhead,
                    Stream.concat(Stream.concat(go(NORTH, trailhead, 0), go(SOUTH, trailhead, 0)),
                                    Stream.concat(go(EAST, trailhead, 0), go(WEST, trailhead, 0)))
                            .collect(Collectors.toSet()));
        });
        return trailheads.values().stream().map(Set::size).reduce(Integer::sum).get();
    }

    private Stream<Vector2> go(Direction direction, Vector2 position, int currentHeight) {
        var newPosition = position.add(direction.vector());
        var newHeight = currentHeight + 1;
        if (map.outOfBounds(newPosition)) {
            return Stream.empty();
        }
        if (map.get(newPosition) == newHeight) {
            if (newHeight == 9) {
                return Stream.of(newPosition);
            }
            return Stream.concat(Stream.concat(go(NORTH, newPosition, newHeight), go(SOUTH, newPosition, newHeight)),
                    Stream.concat(go(EAST, newPosition, newHeight), go(WEST, newPosition, newHeight)));
        }
        return Stream.empty();
    }

    @Override
    public Object part1Answer() {
        return 550;
    }

    @Override
    public Object computePart2() {
        return null;
    }

    @Override
    public Object part2Answer() {
        return null;
    }
}

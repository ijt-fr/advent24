package com.advent.day16;

import static com.advent.util.Direction.EAST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.advent.Puzzle;
import com.advent.util.Direction;
import com.advent.util.Grid;
import com.advent.util.InputUtils;
import com.advent.util.Vector2;

public class ReindeerMaze extends Puzzle {

    private Vector2 start;
    private Vector2 end;
    private Map<Reindeer, Long> unvisited;
    private Map<Reindeer, Long> visited;

    @Override
    public void parseInput(List<String> lines) {
        visited = new HashMap<>();
        Grid<Character> grid = Grid.ofChars(InputUtils.toCharMatrix(lines));
        start = new Vector2(1, grid.maxY() - 2);
        end = new Vector2(grid.maxX() - 2, 1);
        unvisited = grid.stream()
                            .filter(cell -> '#' != cell.entry() && 'S' != cell.entry())
                            .map(Grid.Cell::vector)
                            .flatMap(v -> Direction.stream().map(d -> new Reindeer(v, d)))
                            .collect(Collectors.toMap(r -> r, r -> Long.MAX_VALUE));
    }

    @Override
    public Object computePart1() {
        Optional<Map.Entry<Reindeer, Long>> currentOptional = Optional.of(Map.entry(new Reindeer(start, EAST), 0L));
        while (!unvisited.isEmpty() && currentOptional.isPresent()) {
            Reindeer currentReindeer = currentOptional.get().getKey();
            Long currentDistance = currentOptional.get().getValue();
            Direction.stream()
                    .map(direction -> new Reindeer(currentReindeer.position().add(direction), direction))
                    .forEach(newReindeer -> {
                        long newDistance = currentDistance
                                                   + 1
                                                   + (1000 * Math.abs(newReindeer.facing().ordinal() - currentReindeer.facing().ordinal()));
                        unvisited.computeIfPresent(newReindeer, (ignored, distance) -> Math.min(distance, newDistance));
                    });
            visited.put(currentReindeer, currentDistance);
            unvisited.remove(currentReindeer);
            currentOptional = unvisited.entrySet().stream()
                              .filter(e -> !e.getValue().equals(Long.MAX_VALUE))
                              .min(Map.Entry.comparingByValue());
        }
        return visited.entrySet().stream().filter(e -> e.getKey().position().equals(end))
                       .min(Map.Entry.comparingByValue()).map(Map.Entry::getValue).orElse(0L);
    }

    @Override
    public Object part1Answer() {
        // 177520 too high
        return null;
    }

    @Override
    public Object computePart2() {
        return null;
    }

    @Override
    public Object part2Answer() {
        return null;
    }

    public record Reindeer(Vector2 position, Direction facing) {

    }
}

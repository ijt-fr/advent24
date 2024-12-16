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
    private Grid<Character> grid;

    @Override
    public void parseInput(List<String> lines) {
        grid = Grid.ofChars(InputUtils.toCharMatrix(lines));
        start = new Vector2(1, grid.maxY() - 2);
        end = new Vector2(grid.maxX() - 2, 1);
    }

    @Override
    public Object computePart1() {
        Map<Reindeer, Long> unvisited = grid.stream()
                                                .filter(cell -> '#' != cell.entry() && 'S' != cell.entry())
                                                .map(Grid.Cell::vector)
                                                .flatMap(v -> Direction.stream().map(d -> new Reindeer(v, d)))
                                                .collect(Collectors.toMap(r -> r, r -> Long.MAX_VALUE));
        Map<Reindeer, Long> visited = new HashMap<>();
        Optional<Map.Entry<Reindeer, Long>> currentOptional = Optional.of(Map.entry(new Reindeer(start, EAST), 0L));
        while (!unvisited.isEmpty() && currentOptional.isPresent()) {
            Reindeer currentReindeer = currentOptional.get().getKey();
            Long currentDistance = currentOptional.get().getValue();
            Direction.stream()
                    .map(direction -> new Reindeer(currentReindeer.position().add(direction), direction))
                    .forEach(newReindeer -> {
                        long newDistance = currentDistance + (currentReindeer.facing() == newReindeer.facing() ? 1L : 1001L);
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
        return 135536L;
    }

    @Override
    public Object computePart2() {
        Map<ReindeerWithHistory, Long> unvisited = grid.stream()
                            .filter(cell -> '#' != cell.entry() && 'S' != cell.entry())
                            .map(Grid.Cell::vector)
                            .flatMap(v -> Direction.stream().map(d -> new ReindeerWithHistory(v, d, new HashMap<>())))
                            .collect(Collectors.toMap(r -> r, r -> Long.MAX_VALUE));
        Map<ReindeerWithHistory, Long> visited = new HashMap<>();
        Optional<Map.Entry<ReindeerWithHistory, Long>> currentOptional = Optional.of(Map.entry(new ReindeerWithHistory(start, EAST, new HashMap<>()), 0L));
        while (!unvisited.isEmpty() && currentOptional.isPresent()) {
            ReindeerWithHistory currentReindeer = currentOptional.get().getKey();
            Long currentDistance = currentOptional.get().getValue();
            Direction.stream()
                    .map(direction -> {
                        Map<Vector2, Direction> history = new HashMap<>(currentReindeer.history());
                        var newPos = currentReindeer.position().add(direction);
                        history.put(newPos, direction);
                        return new ReindeerWithHistory(newPos, direction, history);
                    })
                    .forEach(newReindeer -> {
                        long newDistance = currentDistance + (currentReindeer.facing() == newReindeer.facing() ? 1L : 1001L);
                        unvisited.keySet().stream()
                                .filter(r -> r.position().equals(newReindeer.position())
                                                     && r.facing().equals(newReindeer.facing()))
                                .findAny()
                                .ifPresent(r -> {
                                    long distance = unvisited.get(r);
                                    if (newDistance < distance) {
                                        unvisited.remove(r);
                                        unvisited.put(newReindeer, newDistance);
                                    }
                                });
                    });
            visited.put(currentReindeer, currentDistance);
            unvisited.remove(currentReindeer);
            currentOptional = unvisited.entrySet().stream()
                                      .filter(e -> !e.getValue().equals(Long.MAX_VALUE))
                                      .min(Map.Entry.comparingByValue());
        }
        var f = visited.entrySet().stream().filter(e -> e.getKey().position().equals(end))
                        .min(Map.Entry.comparingByValue()).orElseThrow();
        f.getKey().history().forEach((v, d) -> {
            switch (d) {
                case NORTH -> grid.put(v, '^');
                case EAST -> grid.put(v, '>');
                case SOUTH -> grid.put(v, 'v');
                case WEST -> grid.put(v, '<');
            }
        });
        System.out.println(InputUtils.toString(grid.grid()));

        return f.getKey().history().size() + 1;
    }

    @Override
    public Object part2Answer() {
        return null;
    }

    public record Reindeer(Vector2 position, Direction facing) {

    }

    public record ReindeerWithHistory(Vector2 position, Direction facing, Map<Vector2, Direction> history) {

    }
}

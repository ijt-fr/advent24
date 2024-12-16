package com.advent.day16;

import static com.advent.util.Direction.EAST;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
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
        Map<Reindeer, Long> distances = grid.stream()
                                                .filter(cell -> '#' != cell.entry() && 'S' != cell.entry())
                                                .map(Grid.Cell::vector)
                                                .flatMap(v -> Direction.stream().map(d -> new Reindeer(v, d)))
                                                .collect(Collectors.toMap(r -> r, r -> Long.MAX_VALUE));
        distances.put(new Reindeer(start, EAST), 0L);
        Queue<Reindeer> unvisited = new PriorityQueue<>(Comparator.comparing(distances::get));
        unvisited.offer(new Reindeer(start, EAST));
        while (!unvisited.isEmpty()) {
            Reindeer currentReindeer = unvisited.poll();
            Direction.stream()
                    .map(direction -> new Reindeer(currentReindeer.position().add(direction), direction))
                    .forEach(newReindeer -> {
                        long currentDistance = distances.get(currentReindeer);
                        long newDistance = currentDistance + (currentReindeer.facing() == newReindeer.facing() ? 1L : 1001L);
                        if (distances.containsKey(newReindeer) && distances.get(newReindeer) > newDistance) {
                            distances.put(newReindeer, newDistance);
                            unvisited.remove(newReindeer);
                            unvisited.add(newReindeer);
                        }
                    });
            unvisited.remove(currentReindeer);
        }
        return distances.entrySet().stream().filter(e -> e.getKey().position().equals(end))
                       .min(Map.Entry.comparingByValue()).map(Map.Entry::getValue).orElse(0L);
    }

    @Override
    public Object part1Answer() {
        return 135536L;
    }

    @Override
    public Object computePart2() {
        Map<Reindeer, History> distances = grid.stream()
                                                .filter(cell -> '#' != cell.entry() && 'S' != cell.entry())
                                                .map(Grid.Cell::vector)
                                                .flatMap(v -> Direction.stream().map(d -> new Reindeer(v, d)))
                                                .collect(Collectors.toMap(r -> r, r -> new History(Long.MAX_VALUE, null)));
        distances.put(new Reindeer(start, EAST), new History(0L, Set.of(start)));
        Queue<Reindeer> unvisited = new PriorityQueue<>(Comparator.comparing(r -> distances.get(r).distance()));
        unvisited.offer(new Reindeer(start, EAST));
        while (!unvisited.isEmpty()) {
            Reindeer currentReindeer = unvisited.poll();
            Direction.stream()
                    .map(direction -> new Reindeer(currentReindeer.position().add(direction), direction))
                    .forEach(newReindeer -> {
                        History hist = distances.get(currentReindeer);
                        Set<Vector2> newHistory = new HashSet<>(hist.history());
                        newHistory.add(newReindeer.position());

                        long currentDistance = hist.distance();
                        long newDistance = currentDistance + (currentReindeer.facing() == newReindeer.facing() ? 1L : 1001L);
                        if (distances.containsKey(newReindeer)) {
                            var oldNewReindeer = distances.get(newReindeer);
                            if (oldNewReindeer.distance() > newDistance) {
                                distances.put(newReindeer, new History(newDistance, newHistory));
                                unvisited.remove(newReindeer);
                                unvisited.add(newReindeer);
                            } else if (oldNewReindeer.distance() == newDistance) {
                                newHistory.addAll(oldNewReindeer.history());
                                distances.put(newReindeer, new History(newDistance, newHistory));
                            }
                        }
                    });
            unvisited.remove(currentReindeer);
        }
        return distances.entrySet().stream().filter(e -> e.getKey().position().equals(end))
                        .min(Comparator.comparing(e -> e.getValue().distance()))
                       .orElseThrow().getValue().history().size();
    }

    @Override
    public Object part2Answer() {
        return 583;
    }

    public record Reindeer(Vector2 position, Direction facing) {

    }
    
    public record History(Long distance, Set<Vector2> history) {
        
    }
}

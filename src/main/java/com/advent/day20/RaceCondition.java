package com.advent.day20;

import java.util.Comparator;
import java.util.HashMap;
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

public class RaceCondition extends Puzzle {

    private Set<Vector2> walls;
    private Vector2 start;
    private Vector2 end;
    private Grid<Character> grid;

    @Override
    public void parseInput(List<String> lines) {
        grid = Grid.ofChars(InputUtils.toCharMatrix(lines));
        walls = grid.stream()
                .filter(c -> c.entry() == '#')
                .map(Grid.Cell::vector)
                .collect(Collectors.toSet());
        start = grid.stream().filter(c -> c.entry() == 'S').findFirst().orElseThrow().vector();
        end = grid.stream().filter(c -> c.entry() == 'E').findFirst().orElseThrow().vector();
    }

    @Override
    public Object computePart1() {
        Map<Vector2, Long> distancesFromStart = dijkstra(walls, start);
        Map<Vector2, Long> distancesFromEnd = dijkstra(walls, end);
        Long noCheatSpeedRun = distancesFromStart.get(end);
        return distancesFromStart.entrySet().stream().flatMap(entry -> distancesFromEnd.entrySet().stream()
                               .filter(e -> e.getKey().distance(entry.getKey()) <= 2)
                               .map(e -> e.getValue() + e.getKey().distance(entry.getKey()))
                               .map(distanceToEnd -> entry.getValue() + distanceToEnd))
                       .map(cheatingDistance -> noCheatSpeedRun - cheatingDistance)
                       .filter(diff -> diff >= 100)
                       .count();
//                       .count();
//        long base = dijkstra(walls, start);
//        return walls.stream().filter(wall -> {
//            var subset = new HashSet<>(walls);
//            subset.remove(wall);
//
//            if (base - result >= 100) {
//                return true;
//            }
//            return false;
//        }).count();
    }

    private Map<Vector2, Long> dijkstra(Set<Vector2> walls, Vector2 start) {
        Map<Vector2, Long> distances = new HashMap<>();
        for (int x = 0; x < grid.maxX(); x++) {
            for (int y = 0; y < grid.maxY(); y++) {
                Vector2 v = new Vector2(x, y);
                if (!walls.contains(v)) {
                    distances.put(v, Long.MAX_VALUE);
                }
            }
        }
        distances.put(start, 0L);
        Queue<Vector2> unvisited = new PriorityQueue<>(Comparator.comparing(distances::get));
        unvisited.offer(start);
        while (!unvisited.isEmpty()) {
            Vector2 current = unvisited.poll();
            Direction.stream()
                    .map(current::add)
                    .forEach(newVector -> {
                        long currentDistance = distances.get(current);
                        long newDistance = currentDistance + 1L;
                        if (distances.containsKey(newVector) && distances.get(newVector) > newDistance) {
                            distances.put(newVector, newDistance);
                            unvisited.remove(newVector);
                            unvisited.add(newVector);
                        }
                    });
            unvisited.remove(current);
        }
        return distances;
    }

    @Override
    public Object part1Answer() {
        return 1497L;
    }

    @Override
    public Object computePart2() {
        Map<Vector2, Long> distancesFromStart = dijkstra(walls, start);
        Map<Vector2, Long> distancesFromEnd = dijkstra(walls, end);
        Long noCheatSpeedRun = distancesFromStart.get(end);
        return distancesFromStart.entrySet().stream().flatMap(entry -> distancesFromEnd.entrySet().stream()
                                                                               .filter(e -> e.getKey().distance(entry.getKey()) <= 20)
                                                                               .map(e -> e.getValue() + e.getKey().distance(entry.getKey()))
                                                                               .map(distanceToEnd -> entry.getValue() + distanceToEnd))
                       .map(cheatingDistance -> noCheatSpeedRun - cheatingDistance)
                       .filter(diff -> diff >= 100)
                       .count();
    }

    @Override
    public Object part2Answer() {
        return 1030809L;
    }
}

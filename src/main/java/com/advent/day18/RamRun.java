package com.advent.day18;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.advent.Puzzle;
import com.advent.util.Direction;
import com.advent.util.Vector2;

public class RamRun extends Puzzle {

    private static final int SIZE = 1024;
    private static final int MAX_X = 71;
    private static final int MAX_Y = 71;

    private List<Vector2> corruptParts;

    @Override
    public void parseInput(List<String> lines) {
        corruptParts = lines.stream().map(s -> s.split(","))
                .map(a -> new Vector2(Integer.parseInt(a[0]), Integer.parseInt(a[1])))
                               .toList();

    }

    @Override
    public Object computePart1() {
        HashSet<Vector2> corruptSet = new HashSet<>(corruptParts.subList(0, 1024));
        return dijkstra(corruptSet);
    }

    private Long dijkstra(HashSet<Vector2> corruptSet) {
        Map<Vector2, Long> distances = new HashMap<>();
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                Vector2 v = new Vector2(x, y);
                if (!corruptSet.contains(v)) {
                    distances.put(v, Long.MAX_VALUE);
                }
            }
        }
        distances.put(new Vector2(0, 0), 0L);
        Queue<Vector2> unvisited = new PriorityQueue<>(Comparator.comparing(distances::get));
        unvisited.offer(new Vector2(0, 0));
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
        return distances.get(new Vector2(MAX_X - 1, MAX_Y - 1));
    }

    @Override
    public Object part1Answer() {
        return 286L;
    }

    @Override
    public Object computePart2() {
        for (int i = 1024; i < corruptParts.size(); i++) {
            HashSet<Vector2> corruptSet = new HashSet<>(corruptParts.subList(0, i));
            Long result = dijkstra(corruptSet);
            if (result == null || result.equals(Long.MAX_VALUE)) {
                return corruptParts.get(i - 1);
            }
        }
        return null;
    }

    @Override
    public Object part2Answer() {
        return null;
    }
}

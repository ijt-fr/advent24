package com.advent.util;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Dijkstra {

    public static Map<Vector2, Long> dijkstra(Set<Vector2> vectors, Vector2 start,
            BiFunction<Vector2, Direction, Long> distanceCalculator) {
        Map<Vector2, Long> distances = vectors.stream()
                                               .collect(Collectors.toMap(v -> v, v -> Long.MAX_VALUE));
        distances.put(start, 0L);
        Queue<Vector2> unvisited = new PriorityQueue<>(Comparator.comparing(distances::get));
        unvisited.offer(start);
        while (!unvisited.isEmpty()) {
            Vector2 current = unvisited.poll();
            Direction.stream()
                    .forEach(direction -> {
                        long currentDistance = distances.get(current);
                        long newDistance = currentDistance + distanceCalculator.apply(current, direction);
                        Vector2 newVector = current.add(direction);
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
}

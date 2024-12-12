package com.advent.day12;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.advent.Puzzle;
import com.advent.util.Direction;
import com.advent.util.Grid;
import com.advent.util.InputUtils;
import com.advent.util.MultiHashmap;
import com.advent.util.Vector2;

public class GardenGroups extends Puzzle {
    private MultiHashmap<Character, Vector2> charMap;

    @Override
    public void parseInput(List<String> lines) {
        Grid<Character> grid = Grid.ofChars(InputUtils.toCharMatrix(lines));
        charMap = new MultiHashmap<>();
        for (int x = 0; x < grid.maxX(); x++) {
            for (int y = 0; y < grid.maxY(); y++) {
                Character ch = grid.get(x, y);
                Vector2 vector = new Vector2(x, y);
                charMap.add(ch, vector);
            }
        }
    }

    @Override
    public Object computePart1() {
        return charMap.entrySet().stream()
                       .flatMap(this::regions)
                       .map(r -> r.area() * r.perimeter())
                       .reduce(Long::sum).orElse(0L);
    }

    private Stream<Region> regions(Map.Entry<Character, Set<Vector2>> entry) {
        Set<Region> regions = new HashSet<>();
        var key = entry.getKey();
        var value = entry.getValue();
        while (!value.isEmpty()) {
            Vector2 vector = entry.getValue().stream().findFirst().orElseThrow();
            value.remove(vector);
            Set<Vector2> regionSet = new HashSet<>();
            Set<Vector2> adjacent = Set.of(vector);
            while (!adjacent.isEmpty()) {
                regionSet.addAll(adjacent);
                adjacent = adjacent.stream()
                                   .flatMap(v -> findAdjacent(v, value))
                                   .collect(Collectors.toSet());

            }
            regions.add(new Region(key, regionSet));
        }
        return regions.stream();
    }

    private Stream<Vector2> findAdjacent(Vector2 vector, Set<Vector2> set) {
        return Arrays.stream(Direction.values())
                       .map(d -> vector.add(d.vector()))
                       .filter(set::contains)
                       .peek(set::remove);
    }

    @Override
    public Object part1Answer() {
        return 1488414L;
    }

    @Override
    public Object computePart2() {
        return charMap.entrySet().stream()
                       .flatMap(this::regions)
                       .map(r -> r.area() * r.sides())
                       .reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part2Answer() {
        return null;
    }

    record Region(Character type, Set<Vector2> vectors) {

        long perimeter() {
            return vectors.stream()
                           .map(vector -> Arrays.stream(Direction.values())
                                                  .map(d -> vector.add(d.vector()))
                                                  .filter(vectors::contains).count())
                           .map(l -> 4 - l)
                           .reduce(Long::sum).orElse(0L);
        }

        long sides() {
            return 0L;
        }

        long area() {
            return vectors.size();
        }
    }
}

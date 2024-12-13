package com.advent.day12;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
                charMap.add(grid.get(x, y), new Vector2(x, y));
            }
        }
    }

    @Override
    public Object computePart1() {
        return charMap.values().stream()
                       .flatMap(GardenGroups::findRegions)
                       .map(r -> r.area() * r.perimeter())
                       .reduce(Long::sum).orElse(0L);
    }

    private static Stream<Region> findRegions(Set<Vector2> vectors) {
        Set<Region> regions = new HashSet<>();
        while (!vectors.isEmpty()) {
            Vector2 vector = vectors.stream().findFirst().orElseThrow();
            vectors.remove(vector);
            Set<Vector2> regionVectors = new HashSet<>();
            Set<Vector2> adjacent = Set.of(vector);
            while (!adjacent.isEmpty()) {
                regionVectors.addAll(adjacent);
                adjacent = adjacent.stream()
                                   .flatMap(v -> findAdjacent(v, vectors))
                                   .collect(Collectors.toSet());

            }
            Set<Plot> plots = new HashSet<>();
            regionVectors.forEach(v -> {
                plots.add(new Plot(v, Arrays.stream(Direction.values())
                                            .filter(d -> !regionVectors.contains(v.add(d.vector())))
                                            .collect(Collectors.toSet())));
            });
            regions.add(new Region(plots));
        }
        return regions.stream();
    }

    private static Stream<Vector2> findAdjacent(Vector2 vector, Set<Vector2> vectors) {
        return Arrays.stream(Direction.values())
                       .map(d -> vector.add(d.vector()))
                       .filter(vectors::contains)
                       .peek(vectors::remove);
    }

    @Override
    public Object part1Answer() {
        return 1488414L;
    }

    @Override
    public Object computePart2() {
        return charMap.values().stream()
                       .flatMap(GardenGroups::findRegions)
                       .map(r -> r.area() * r.sides())
                       .reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part2Answer() {
        return 911750L;
    }

    record Region(Set<Plot> plots) {

        long perimeter() {
            return plots.stream().map(Plot::edges)
                           .map(Set::size)
                           .map(i -> (long) i)
                           .reduce(Long::sum).orElse(0L);
        }

        long sides() {
            MultiHashmap<Direction, Vector2> inverse = new MultiHashmap<>();
            plots.forEach(plot -> {
                plot.edges().forEach(edge -> {
                    inverse.add(edge, plot.vector());
                });
            });
            return inverse.values().stream()
                    .flatMap(GardenGroups::findRegions)
                    .count();
        }

        long area() {
            return plots.size();
        }
    }

    record Plot(Vector2 vector, Set<Direction> edges) {

    }
}

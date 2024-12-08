package com.advent.day8;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.advent.Puzzle;
import com.advent.util.InputUtils;
import com.advent.util.Vector2;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

public class ResonantCollinearity extends Puzzle {
    private ListMultimap<Character, Vector2> antenas;
    private int xboundary;
    private int yboundary;
    private char[][] matrix;

    @Override
    public void parseInput(List<String> lines) {
        matrix = InputUtils.toCharMatrix(lines);
        xboundary = matrix[0].length;
        yboundary = matrix.length;
        antenas = MultimapBuilder.hashKeys().arrayListValues().build();
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] != '.') {
                    antenas.put(matrix[j][i], new Vector2(i, j));
                }
            }
        }
    }

    @Override
    public Object computePart1() {
        Set<Vector2> antinodes = new HashSet<>();
        antenas.asMap().values().forEach(a -> {
            for (int i = 0; i < a.size(); i++) {
                for (int j = i + 1; j < a.size(); j++) {
                    var antena1 = ((List<Vector2>) a).get(i);
                    var antena2 = ((List<Vector2>) a).get(j);
                    getPart1Antinodes(antinodes, antena1, antena2);
                }
            }
        });
        return antinodes.size();
    }

    private void getPart1Antinodes(Set<Vector2> antinodes, Vector2 antena1, Vector2 antena2) {
        var diff = antena1.minus(antena2);
        var antinode1 = antena1.add(diff);
        if (inBounds(antinode1)) {
            antinodes.add(antinode1);
        }
        var antinode2 = antena2.minus(diff);
        if (inBounds(antinode2)) {
            antinodes.add(antinode2);
        }
    }

    private boolean inBounds(Vector2 antinode) {
        return antinode.x() < xboundary && antinode.y() < yboundary && antinode.x() >= 0 && antinode.y() >= 0;
    }

    @Override
    public Object part1Answer() {
        return 351;
    }

    @Override
    public Object computePart2() {
        Set<Vector2> antinodes = new HashSet<>();
        antenas.asMap().values().forEach(a -> {
            for (int i = 0; i < a.size(); i++) {
                for (int j = i + 1; j < a.size(); j++) {
                    var antena1 = ((List<Vector2>) a).get(i);
                    var antena2 = ((List<Vector2>) a).get(j);
                    getPart2Antinodes(antinodes, antena1, antena2);
                }
            }
        });
        return antinodes.size();
    }

    private void getPart2Antinodes(Set<Vector2> antinodes, Vector2 antena1, Vector2 antena2) {
        antinodes.add(antena1);
        antinodes.add(antena2);
        var diff = antena1.minus(antena2);
        var antinode1 = antena1.add(diff);
        while (inBounds(antinode1)) {
            antinodes.add(antinode1);
            antinode1 = antinode1.add(diff);
        }
        var antinode2 = antena2.minus(diff);
        while (inBounds(antinode2)) {
            antinodes.add(antinode2);
            antinode2 = antinode2.minus(diff);
        }
    }

    @Override
    public Object part2Answer() {
        return 1259;
    }
}

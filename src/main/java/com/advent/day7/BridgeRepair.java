package com.advent.day7;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.advent.Puzzle;

public class BridgeRepair extends Puzzle {

    private Set<Equation> equations;

    @Override
    public void parseInput(List<String> lines) {
        equations = lines.stream()
                .map(line -> {
                    var var = line.split(": ");
                    var result = Long.parseLong(var[0]);
                    var numbers = Arrays.stream(var[1].split(" "))
                                          .map(Long::parseLong)
                                          .toList();
                    return new Equation(result, numbers);
                }).collect(Collectors.toSet());
    }

    @Override
    public Object computePart1() {
        return equations.stream().map(e -> e.solve(false)).reduce(Long::sum).orElseThrow();
    }

    @Override
    public Object part1Answer() {
        return 3245122495150L;
    }

    @Override
    public Object computePart2() {
        return equations.stream().map(e -> e.solve(true)).reduce(Long::sum).orElseThrow();
    }

    @Override
    public Object part2Answer() {
        return 105517128211543L;
    }

}

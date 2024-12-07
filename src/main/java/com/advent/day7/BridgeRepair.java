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
        return equations.stream().map(Equation::solve).reduce(Long::sum).orElseThrow();
    }

    @Override
    public Object part1Answer() {
        return 3245122495150L;
    }

    @Override
    public Object computePart2() {
        return null;
    }

    @Override
    public Object part2Answer() {
        return null;
    }

    private record Equation(long result, List<Long> numbers) {

        // will return 0 if not solvable
        long solve() {
            long total = 0L;
            long solution = add(total, numbers.getFirst(), numbers.subList(1, numbers.size()));
            if (solution == result) {
                return result;
            }
            solution = mult(total, numbers.getFirst(), numbers.subList(1, numbers.size()));
            if (solution == result) {
                return result;
            }
            return 0L;
        }

        private long mult(long total, Long next, List<Long> remaining) {
            total = total * next;
            if (remaining.isEmpty()) {
                return total;
            }
            long solution = add(total, remaining.get(0), remaining.subList(1, remaining.size()));
            if (solution == result) {
                return solution;
            }
            solution = mult(total, remaining.get(0), remaining.subList(1, remaining.size()));
            if (solution == result) {
                return result;
            }
            return 0;
        }

        private long add(long total, Long next, List<Long> remaining) {
            total = total + next;
            if (remaining.isEmpty()) {
                return total;
            }
            long solution = add(total, remaining.get(0), remaining.subList(1, remaining.size()));
            if (solution == result) {
                return solution;
            }
            solution = mult(total, remaining.get(0), remaining.subList(1, remaining.size()));
            if (solution == result) {
                return result;
            }
            return 0;
        }

    }
}

package com.advent.day17;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.advent.Puzzle;

public class ChronospatialComputer extends Puzzle {
    private long a;
    private long b;
    private long c;
    private int[] instructions;
    private List<Long> instructionsList;

    @Override
    public void parseInput(List<String> lines) {
        a = Long.parseLong(StringUtils.substringAfter(lines.get(0), "Register A: "));
        b = Long.parseLong(StringUtils.substringAfter(lines.get(1), "Register B: "));
        c = Long.parseLong(StringUtils.substringAfter(lines.get(2), "Register C: "));
        instructions = Arrays.stream(StringUtils.substringAfter(lines.get(4), "Program: ").split(","))
                .mapToInt(Integer::parseInt).toArray();
        instructionsList = Arrays.stream(instructions).boxed().map(i -> (long) i).toList();
    }

    @Override
    public Object computePart1() {
        SevenBitComputer computer = new SevenBitComputer(a, b, c, instructions);
        return computer.run().stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public Object part1Answer() {
        return "2,7,6,5,6,0,2,3,1";
    }

    @Override
    public Object computePart2() {
        int index = instructionsList.size() - 1;
        long multiplier = 1L << (3 * (index));
        a = multiplier;
        while (index >= 0) {
            var out = new SevenBitComputer(a, b, c, instructions).run();
            if (out.subList(index, out.size()).equals(instructionsList.subList(index, instructionsList.size()))) {
                index--;
                multiplier = 1L << (3 * (index));
            } else {
                a += multiplier;
            }
        }
        return a;
    }

    @Override
    public Object part2Answer() {
        return 107416870455451L;
    }

}

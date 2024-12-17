package com.advent.day17;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.advent.Puzzle;

public class ChronospatialComputer extends Puzzle {
    private long a;
    private long b;
    private long c;
    private int[] instructions;

    @Override
    public void parseInput(List<String> lines) {
        a = Long.parseLong(StringUtils.substringAfter(lines.get(0), "Register A: "));
        b = Long.parseLong(StringUtils.substringAfter(lines.get(1), "Register B: "));
        c = Long.parseLong(StringUtils.substringAfter(lines.get(2), "Register C: "));
        instructions = Arrays.stream(StringUtils.substringAfter(lines.get(4), "Program: ").split(","))
                .mapToInt(Integer::parseInt).toArray();
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
        List<Long> instructionsList = Arrays.stream(instructions).boxed().map(i -> (long) i).toList();
        a = 35_150_000_000_000L;

        Runnable helloRunnable = () -> System.out.println(a);
        try (var executor = Executors.newScheduledThreadPool(1)) {
            executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);
            boolean isMatch = false;
            while (!isMatch) {
                a++;
                isMatch = true;
                var computer = new SevenBitComputer(a, b, c, instructions);
                for (Long instruct : instructionsList) {
                    long next = computer.next();
                    if (next != instruct) {
                        isMatch = false;
                        break;
                    }
                }
            }
        }
        return a;
    }

    @Override
    public Object part2Answer() {
        return null;
    }

}

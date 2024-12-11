package com.advent.day11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.advent.Puzzle;

public class PlutonianPebbles extends Puzzle {

    Stream<Stone> stones;

    @Override
    public void parseInput(List<String> lines) {
        stones = Arrays.stream(lines.getFirst().split(" "))
                .map(Long::parseLong)
                .map(Stone::new);
    }

    @Override
    public Object computePart1() {
        for (int i = 0; i < 25; i++) {
            stones = stones.flatMap(Stone::blink);
        }
        return stones.count();
    }

    @Override
    public Object part1Answer() {
        return 231278L;
    }

    @Override
    public Object computePart2() {
        for (int i = 0; i < 75; i++) {
            stones = stones.flatMap(Stone::blink);
        }
        return stones.count();
    }

    @Override
    public Object part2Answer() {
        return null;
    }

    record Stone(Long number) {

        Stream<Stone> blink() {
            if (number == 0) {
                return Stream.of(new Stone(1L));
            }
            var digits = digits();
            if (digits % 2 == 0) {
                long multiple = (long) Math.pow(10, digits / 2);
                return Stream.of(new Stone(upper(multiple)), new Stone(lower(multiple)));
            }
            return Stream.of(new Stone(number * 2024L));
        }

        private int digits() {
            return (int) (Math.log10(Math.abs(number)) + 1);
        }

        private long upper(long multiple) {
            return number / multiple;
        }

        private long lower(long multiple) {
            return number % multiple;
        }
    }
}

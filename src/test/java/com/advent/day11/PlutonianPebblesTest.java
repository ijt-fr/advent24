package com.advent.day11;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class PlutonianPebblesTest extends BasePuzzleTest<PlutonianPebbles> {

    private static final String EXAMPLE = "0 1 10 99 999";

    private static final String EXAMPLE_2 = "125 17";

    @Override
    public PlutonianPebbles getPuzzle() {
        return new PlutonianPebbles();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE_2, 55312L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.empty();
    }
}

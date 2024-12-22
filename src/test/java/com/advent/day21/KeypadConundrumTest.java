package com.advent.day21;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class KeypadConundrumTest extends BasePuzzleTest<KeypadConundrum> {

    private static final String EXAMPLE = """
            029A
            980A
            179A
            456A
            379A
            """;

    @Override
    public KeypadConundrum getPuzzle() {
        return new KeypadConundrum();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE, 126384L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.empty();
    }
}

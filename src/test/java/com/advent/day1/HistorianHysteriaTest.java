package com.advent.day1;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class HistorianHysteriaTest extends BasePuzzleTest<HistorianHysteria> {

    private static final String SAMPLE_INPUT = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
            """;

    @Override
    public HistorianHysteria getPuzzle() {
        return new HistorianHysteria();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(
                Arguments.of(SAMPLE_INPUT, 11L)
        );
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(
                Arguments.of(SAMPLE_INPUT, 31L)
        );
    }
}

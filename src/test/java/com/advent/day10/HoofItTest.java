package com.advent.day10;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class HoofItTest extends BasePuzzleTest<HoofIt> {

    private static final String EXAMPLE_1 = """
            0123
            1234
            8765
            9876
            """;

    private static final String EXAMPLE_5 = """
            89010123
            78121874
            87430965
            96549874
            45678903
            32019012
            01329801
            10456732
            """;

    @Override
    public HoofIt getPuzzle() {
        return new HoofIt();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE_1, 1),
                Arguments.of(EXAMPLE_5, 36));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of();
    }
}

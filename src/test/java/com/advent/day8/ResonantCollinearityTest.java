package com.advent.day8;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class ResonantCollinearityTest extends BasePuzzleTest<ResonantCollinearity> {

    private static final String EXAMPLE_1 = """
            ............
            ........0...
            .....0......
            .......0....
            ....0.......
            ......A.....
            ............
            ............
            ........A...
            .........A..
            ............
            ............
            """;
    @Override
    public ResonantCollinearity getPuzzle() {
        return new ResonantCollinearity();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE_1, 14));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.empty();
    }
}

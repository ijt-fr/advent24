package com.advent.day14;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class RestroomRedoubtTest extends BasePuzzleTest<RestroomRedoubt> {

    private static final String EXAMPLE = """
            p=0,4 v=3,-3
            p=6,3 v=-1,-3
            p=10,3 v=-1,2
            p=2,0 v=2,-1
            p=0,0 v=1,3
            p=3,0 v=-2,-2
            p=7,6 v=-1,-3
            p=3,0 v=-1,-2
            p=9,3 v=2,3
            p=7,3 v=-1,2
            p=2,4 v=2,-3
            p=9,5 v=-3,-3
            """;

    @Override
    public RestroomRedoubt getPuzzle() {
        return new RestroomRedoubt();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE, 12L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.empty();
    }
}

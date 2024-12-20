package com.advent.day18;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class RamRunTest extends BasePuzzleTest<RamRun> {

    private static final String EXAMPLE = """
            5,4
            4,2
            4,5
            3,0
            2,1
            6,3
            2,4
            1,5
            0,6
            3,3
            2,6
            5,1
            1,2
            5,5
            2,5
            6,5
            1,4
            0,4
            6,4
            1,1
            6,1
            1,0
            0,5
            1,6
            2,0
            """;
    @Override
    public RamRun getPuzzle() {
        return new RamRun();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.empty();
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.empty();
    }
}

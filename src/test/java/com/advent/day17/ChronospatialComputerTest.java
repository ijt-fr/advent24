package com.advent.day17;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class ChronospatialComputerTest extends BasePuzzleTest<ChronospatialComputer> {

    private static final String EXAMPLE = """
            Register A: 729
            Register B: 0
            Register C: 0
            
            Program: 0,1,5,4,3,0
            """;

    private static final String EXAMPLE_2 = """
            Register A: 2024
            Register B: 0
            Register C: 0
            
            Program: 0,3,5,4,3,0
            """;

    @Override
    public ChronospatialComputer getPuzzle() {
        return new ChronospatialComputer();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE, "4,6,3,5,6,3,5,2,1,0"));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(EXAMPLE_2, 117440L));
    }
}

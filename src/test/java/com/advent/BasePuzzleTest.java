package com.advent;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BasePuzzleTest<T extends Puzzle> {

    private T puzzle;

    public abstract T getPuzzle();

    public abstract Stream<Arguments> part1Samples();

    public abstract Stream<Arguments> part2Samples();

    @BeforeEach
    void setUp() {
        puzzle = getPuzzle();
    }

    @ParameterizedTest
    @MethodSource("part1Samples")
    public void testSamplePart1(String input, Object expected) {
        puzzle.parseInput((List.of(input.split("\n"))));
        assertThat(puzzle.computePart1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("part2Samples")
    public void testSamplePart2(String input, Object expected) {
        puzzle.parseInput((List.of(input.split("\n"))));
        assertThat(puzzle.computePart2()).isEqualTo(expected);
    }

}

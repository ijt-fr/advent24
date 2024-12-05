package com.advent.day4;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class CeresSearchTest extends BasePuzzleTest<CeresSearch> {

    private static final String SAMPLE =
            """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
            """;

    private static final Long EXPECTED_1 = 18L;
    private static final Long EXPECTED_2 = 9L;

    @Override
    public CeresSearch getPuzzle() {
        return new CeresSearch();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(SAMPLE, EXPECTED_1));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(SAMPLE, EXPECTED_2));
    }
}

package com.advent.day4;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.advent.Puzzle;

public class CeresSearchTest {

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

    private Puzzle puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new CeresSearch();
    }

    @Test
    public void testSamplePart1() {
        assertThat(puzzle.computePart1(List.of(SAMPLE.split("\n")))).isEqualTo(EXPECTED_1);
    }

    @Test
    public void testSamplePart2() {
        assertThat(puzzle.computePart2(List.of(SAMPLE.split("\n")))).isEqualTo(EXPECTED_2);
    }
}

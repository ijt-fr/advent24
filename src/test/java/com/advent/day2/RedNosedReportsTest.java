package com.advent.day2;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.advent.Puzzle;

public class RedNosedReportsTest {

    private static final String SAMPLE_INPUT =
            """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
            """;
    private static final Long EXPECTED_1 = 2L;
    private static final Long EXPECTED_2 = 4L;

    @Test
    public void testSamplePart1() {
        Puzzle puzzle = new RedNosedReports();
        assertThat(puzzle.computePart1(List.of(SAMPLE_INPUT.split("\n")))).isEqualTo(EXPECTED_1);
    }

    @Test
    public void testSamplePart2() {
        Puzzle puzzle = new RedNosedReports();
        assertThat(puzzle.computePart2(List.of(SAMPLE_INPUT.split("\n")))).isEqualTo(EXPECTED_2);
    }
}

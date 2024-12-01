package com.advent.day1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.advent.Puzzle;

public class HistorianHysteriaTest {

    String sampleInput = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
            """;

    @Test
    public void testSamplePart1() {
        Puzzle puzzle = new HistorianHysteria();
        assertThat(puzzle.computePart1(List.of(sampleInput.split("\n")))).isEqualTo(11L);
    }

    @Test
    public void testSamplePart2() {
        Puzzle puzzle = new HistorianHysteria();
        assertThat(puzzle.computePart2(List.of(sampleInput.split("\n")))).isEqualTo(31L);
    }
}

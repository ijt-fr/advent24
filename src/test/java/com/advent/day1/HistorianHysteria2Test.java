package com.advent.day1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.advent.Puzzle;

public class HistorianHysteria2Test {

    String sampleInput = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
            """;

    @Test
    public void testSample() {
        Puzzle puzzle = new HistorianHysteria2();
        assertThat(puzzle.computeAnswer(List.of(sampleInput.split("\n")))).isEqualTo(31L);
    }
}

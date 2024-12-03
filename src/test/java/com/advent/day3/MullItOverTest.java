package com.advent.day3;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MullItOverTest {
    private static final String SAMPLE_INPUT =
            """
            xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
            """;

    private static final String SAMPLE2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

    private static final Long EXPECTED_1 = 161L;
    private static final Long EXPECTED_2 = 48L;

    private MullItOver puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new MullItOver();
    }

    @Test
    public void testSamplePart1() {
        assertThat(puzzle.computePart1(List.of(SAMPLE_INPUT.split("\n")))).isEqualTo(EXPECTED_1);
    }

    @Test
    public void testSamplePart2() {
        assertThat(puzzle.computePart2(List.of(SAMPLE2.split("\n")))).isEqualTo(EXPECTED_2);
    }
}

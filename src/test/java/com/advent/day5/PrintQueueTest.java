package com.advent.day5;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.advent.Puzzle;

public class PrintQueueTest {

    private static final String INPUT = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13
            
            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
            """;

    private static final Long EXPECTED_1 = 143L;
    private static final Long EXPECTED_2 = 123L;

    private Puzzle puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new PrintQueue();
    }

    @Test
    public void testSamplePart1() {
        assertThat(puzzle.computePart1(List.of(INPUT.split("\n")))).isEqualTo(EXPECTED_1);
    }

    @Test
    public void testSamplePart2() {
        assertThat(puzzle.computePart2(List.of(INPUT.split("\n")))).isEqualTo(EXPECTED_2);
    }
}

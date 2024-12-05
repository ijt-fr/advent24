package com.advent.day5;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class PrintQueueTest extends BasePuzzleTest<PrintQueue> {

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

    @Override
    public PrintQueue getPuzzle() {
        return new PrintQueue();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(INPUT, EXPECTED_1));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(INPUT, EXPECTED_2));
    }
}

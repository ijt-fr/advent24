package com.advent.day22;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class MonkeyMarketTest extends BasePuzzleTest<MonkeyMarket> {

    private static final String EXAMPLE = """
            1
            10
            100
            2024
            """;

    private static final String EXAMPLE_2 = """
1
2
3
2024
""";

    @Override
    public MonkeyMarket getPuzzle() {
        return new MonkeyMarket();
    }

    @Test
    void test123() {
        MonkeyMarket puzzle = getPuzzle();
        assertThat(puzzle.next(123L)).isEqualTo(15887950L);
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE, 37327623L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(EXAMPLE_2, 23));
    }

    @Test
    void test123Diffs() {
        MonkeyMarket puzzle = getPuzzle();
        assertThat(puzzle.getPreviousDiffsIntegerMap(123L, 10)).isEqualTo(15887950L);
    }
}

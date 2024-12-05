package com.advent.day2;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class RedNosedReportsTest extends BasePuzzleTest<RedNosedReports> {

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

    @Override
    public RedNosedReports getPuzzle() {
        return new RedNosedReports();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(SAMPLE_INPUT, EXPECTED_1));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(SAMPLE_INPUT, EXPECTED_2));
    }
}

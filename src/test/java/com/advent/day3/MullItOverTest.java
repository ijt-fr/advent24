package com.advent.day3;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class MullItOverTest extends BasePuzzleTest<MullItOver> {
    private static final String SAMPLE_1 = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
    private static final String SAMPLE_2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

    private static final Long EXPECTED_1 = 161L;
    private static final Long EXPECTED_2 = 48L;

    @Override
    public MullItOver getPuzzle() {
        return new MullItOver();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(SAMPLE_1, EXPECTED_1));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(SAMPLE_2, EXPECTED_2));
    }
}

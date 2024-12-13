package com.advent.day13;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class ClawContraptionTest extends BasePuzzleTest<ClawContraption> {


    private static final String EXAMPLE =
            """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400
           \s
            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176
           \s
            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450
           \s
            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
           \s""";

    @Override
    public ClawContraption getPuzzle() {
        return new ClawContraption();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE, 480L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(EXAMPLE, 0L));
    }
}

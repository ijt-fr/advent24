package com.advent.day7;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class BridgeRepairTest extends BasePuzzleTest<BridgeRepair> {

    private static final String SAMPLE = """
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
            """;

    @Override
    public BridgeRepair getPuzzle() {
        return new BridgeRepair();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(SAMPLE, 3749L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of();
    }
}

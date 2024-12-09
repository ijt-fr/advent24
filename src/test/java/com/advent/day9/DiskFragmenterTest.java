package com.advent.day9;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class DiskFragmenterTest extends BasePuzzleTest<DiskFragmenter> {

    private static final String EXAMPLE_1 = """
            12345
            """;
    private static final String EXAMPLE_2 = """
            2333133121414131402
            """;

    @Override
    public DiskFragmenter getPuzzle() {
        return new DiskFragmenter();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE_2, 1928L), Arguments.of(EXAMPLE_1, 60L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.empty();
    }
}

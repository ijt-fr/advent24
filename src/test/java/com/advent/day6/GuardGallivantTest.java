package com.advent.day6;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class GuardGallivantTest extends BasePuzzleTest<GuardGallivant> {

    String INPUT =
            """
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
            """;

    @Override
    public GuardGallivant getPuzzle() {
        return new GuardGallivant();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(INPUT, 41));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(INPUT, 6L));
    }
}

package com.advent.day12;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class GardenGroupsTest extends BasePuzzleTest<GardenGroups> {

    private static final String EXAMPLE_1 = """
            AAAA
            BBCD
            BBCC
            EEEC
            """;

    private static final String EXAMPLE_2 = """
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO
            """;

    private static final String EXAMPLE_3 = """
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
            """;

    private static final String EXAMPLE_4 = """
            EEEEE
            EXXXX
            EEEEE
            EXXXX
            EEEEE
            """;

    private static final String EXAMPLE_5 = """
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
            """;



    @Override
    public GardenGroups getPuzzle() {
        return new GardenGroups();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE_1, 140L),
                Arguments.of(EXAMPLE_2, 772L),
                Arguments.of(EXAMPLE_3, 1930L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(EXAMPLE_1, 80L),
                Arguments.of(EXAMPLE_2, 436L),
                Arguments.of(EXAMPLE_3, 1206L),
                Arguments.of(EXAMPLE_4, 236L),
                Arguments.of(EXAMPLE_5, 368L));
    }
}

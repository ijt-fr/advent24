package com.advent.day16;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class ReindeerMazeTest extends BasePuzzleTest<ReindeerMaze> {

    private static final String EXAMPLE_1 = """
            ###############
            #.......#....E#
            #.#.###.#.###.#
            #.....#.#...#.#
            #.###.#####.#.#
            #.#.#.......#.#
            #.#.#####.###.#
            #...........#.#
            ###.#.#####.#.#
            #...#.....#.#.#
            #.#.#.###.#.#.#
            #.....#...#.#.#
            #.###.#.#.#.#.#
            #S..#.....#...#
            ###############
            """;

    private static final String EXAMPLE_2 = """
            #################
            #...#...#...#..E#
            #.#.#.#.#.#.#.#.#
            #.#.#.#...#...#.#
            #.#.#.#.###.#.#.#
            #...#.#.#.....#.#
            #.#.#.#.#.#####.#
            #.#...#.#.#.....#
            #.#.#####.#.###.#
            #.#.#.......#...#
            #.#.###.#####.###
            #.#.#...#.....#.#
            #.#.#.#####.###.#
            #.#.#.........#.#
            #.#.#.#########.#
            #S#.............#
            #################
            """;

    private static final String EXAMPLE_3 = """
            ##########
            #.......E#
            #.#.#.##.#
            #S#...#..#
            ##########
            """;

    @Override
    public ReindeerMaze getPuzzle() {
        return new ReindeerMaze();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE_1, 7036L),
                Arguments.of(EXAMPLE_2, 11048L),
                Arguments.of(EXAMPLE_3, 5013L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.empty();
    }
}

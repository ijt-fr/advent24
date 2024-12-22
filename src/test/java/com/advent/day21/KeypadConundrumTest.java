package com.advent.day21;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class KeypadConundrumTest extends BasePuzzleTest<KeypadConundrum2> {

    private static final String EXAMPLE = """
            029A
            980A
            179A
            456A
            379A
            """;

    @Override
    public KeypadConundrum2 getPuzzle() {
        return new KeypadConundrum2();
    }

    @Test
    void test0() {
        // A -> 0
        //     <A
        // v<<A ^>>A
        assertThat(getPuzzle().goToNumber('A', '0')).isEqualTo(8L);
    }


    @Test
    void test1() {
        // A -> 1
        //     ^<<A
        // <A v<A A >>^A
        assertThat(getPuzzle().goToNumber('A', '1')).isEqualTo(10L);
    }

    @Test
    void test7() {
        // A => 7
        //  ^^^<<A
        // <AAAv<AA>>^A
        assertThat(getPuzzle().goToNumber('A', '7')).isEqualTo(12L);
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE, 126384L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.empty();
    }
}

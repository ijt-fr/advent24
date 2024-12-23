package com.advent.day21;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class KeypadConundrumTest extends BasePuzzleTest<KeypadConundrum> {

    private static final String EXAMPLE = """
            029A
            980A
            179A
            456A
            379A
            """;

    @Override
    public KeypadConundrum getPuzzle() {
        return new KeypadConundrum();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE, 126384L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.empty();
    }

    @Test
    void testUpToRightDepth1() {
        Long distance = KeypadConundrum.compute(KeypadConundrum.DirectionalButton.UP,
                KeypadConundrum.DirectionalButton.RIGHT, 1);
        assertThat(distance).isEqualTo(2);
    }

    @Test
    void testUpToLeftDepth1() {
        Long distance = KeypadConundrum.compute(KeypadConundrum.DirectionalButton.UP,
                KeypadConundrum.DirectionalButton.LEFT, 1);
        assertThat(distance).isEqualTo(2);
    }

    @Test
    void testAToLeftDepth1() {
        Long distance = KeypadConundrum.compute(KeypadConundrum.DirectionalButton.A,
                KeypadConundrum.DirectionalButton.LEFT, 1);
        assertThat(distance).isEqualTo(3);
    }

    @Test
    void testAToRightDepth2() {
        // vA
        // v<A ^>A
        Long distance = KeypadConundrum.compute(KeypadConundrum.DirectionalButton.A,
                KeypadConundrum.DirectionalButton.RIGHT, 2);
        assertThat(distance).isEqualTo(3);
    }
}

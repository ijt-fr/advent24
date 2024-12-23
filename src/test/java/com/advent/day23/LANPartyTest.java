package com.advent.day23;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.advent.BasePuzzleTest;

public class LANPartyTest extends BasePuzzleTest<LANParty> {

    private static final String EXAMPLE = """
            kh-tc
            qp-kh
            de-cg
            ka-co
            yn-aq
            qp-ub
            cg-tb
            vc-aq
            tb-ka
            wh-tc
            yn-cg
            kh-ub
            ta-co
            de-co
            tc-td
            tb-wq
            wh-td
            ta-ka
            td-qp
            aq-cg
            wq-ub
            ub-vc
            de-ta
            wq-aq
            wq-vc
            wh-yn
            ka-de
            kh-ta
            co-tc
            wh-qp
            tb-vc
            td-yn
            """;

    @Override
    public LANParty getPuzzle() {
        return new LANParty();
    }

    @Override
    public Stream<Arguments> part1Samples() {
        return Stream.of(Arguments.of(EXAMPLE, 7L));
    }

    @Override
    public Stream<Arguments> part2Samples() {
        return Stream.of(Arguments.of(EXAMPLE, "co,de,ka,ta"));
    }
}

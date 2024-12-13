package com.advent.day13;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.advent.Puzzle;
import com.advent.util.Vector2;

public class ClawContraption extends Puzzle {

    private static final Pattern A_PATTERN = Pattern.compile("Button A: X(\\+\\d+), Y(\\+\\d+)");
    private static final Pattern B_PATTERN = Pattern.compile("Button B: X(\\+\\d+), Y(\\+\\d+)");
    private static final Pattern PRIZE_PATTERN = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

    Set<ClawMachine> machines;

    @Override
    public void parseInput(List<String> lines) {
        machines = new HashSet<>();
        var it = lines.iterator();
        while(it.hasNext()) {
            var aVector = getVector(it.next().strip(), A_PATTERN);
            var bVector = getVector(it.next().strip(), B_PATTERN);
            var prizeVector = getVector(it.next().strip(), PRIZE_PATTERN);
            machines.add(new ClawMachine(aVector, bVector, prizeVector));
            if (it.hasNext()) {
                it.next();
            }
        }
    }

    private static Vector2 getVector(String line, Pattern pattern) {
        var aMatcher = pattern.matcher(line);
        if (aMatcher.matches()) {
            var ax = Integer.parseInt(aMatcher.group(1));
            var ay = Integer.parseInt(aMatcher.group(2));
            return new Vector2(ax, ay);
        } else {
            throw new IllegalStateException("Match expected");
        }
    }

    @Override
    public Object computePart1() {
        return machines.stream().map(ClawMachine::findMinTokens).reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part1Answer() {
        return null;
    }

    @Override
    public Object computePart2() {
        return null;
    }

    @Override
    public Object part2Answer() {
        return null;
    }

    static final class ClawMachine {
        private final Vector2 a;
        private final Vector2 b;
        private final Vector2 prize;

        ClawMachine(Vector2 a, Vector2 b, Vector2 prize) {
            this.a = a;
            this.b = b;
            this.prize = prize;
        }

        long findMinTokens() {
            return pressB(0, 0, prize);
        }

        private int pressB(int tokens, int presses, Vector2 current) {
            tokens += 1;
            presses++;
            current = current.minus(b);

            if (current.x() == 0 && current.y() == 0) {
                // only b presses needed
                return tokens;
            }
            if (current.x() < 0 || current.y() < 0) {
                // failed to find the prize
                return 0;
            }
            if (presses >= 100) {
                // fail - too many presses
                return 0;
            }
            // check if remainder is divisible by A
            if (current.x() % a.x() == 0 && current.y() % a.y() == 0) {
                int xDivisor = current.x() / a.x();
                if (xDivisor == current.y() / a.y()) {
                    return tokens + (3 * xDivisor);
                }
            }

            return pressB(tokens, presses, current);
        }

    }
}

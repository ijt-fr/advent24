package com.advent.day13;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.advent.Puzzle;
import com.advent.util.BigVector2;

public class ClawContraption extends Puzzle {

    private static final Pattern PATTERN = Pattern.compile("Button A: X(\\+\\d+), Y(\\+\\d+)Button B: X(\\+\\d+), Y(\\+\\d+)Prize: X=(\\d+), Y=(\\d+)");

    Set<ClawMachine> machines;

    @Override
    public void parseInput(List<String> lines) {
        machines = new HashSet<>();
        var it = lines.iterator();
        while(it.hasNext()) {
            var clawStr = it.next().trim() + it.next().trim() + it.next().trim();
            var matcher = PATTERN.matcher(clawStr);
            if (matcher.matches()) {
                var ax = Long.parseLong(matcher.group(1));
                var ay = Long.parseLong(matcher.group(2));
                var bx = Long.parseLong(matcher.group(3));
                var by = Long.parseLong(matcher.group(4));
                var prizeX = Long.parseLong(matcher.group(5));
                var prizeY = Long.parseLong(matcher.group(6));
                machines.add(new ClawMachine(new Equation(ax, bx, prizeX), new Equation(ay, by, prizeY)));
            } else {
                // shouldn't happen for valid inputs
                throw new IllegalStateException("Invalid input");
            }
            if (it.hasNext()) {
                // skip the blank line
                it.next();
            }
        }
    }

    @Override
    public Object computePart1() {
        return machines.stream()
                       .map(ClawMachine::solve)
                       .map(v -> (v.x() * 3) + v.y())
                       .reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part1Answer() {
        return 40369L;
    }

    @Override
    public Object computePart2() {
        return machines.stream()
                       .map(machine ->
                                    new ClawMachine(new Equation(machine.equation1.a, machine.equation1.b, machine.equation1.c + 10000000000000L),
                                            new Equation(machine.equation2.a, machine.equation2.b, machine.equation2.c + 10000000000000L)))
                       .map(ClawMachine::solve)
                       .map(v -> (v.x() * 3) + v.y())
                       .reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part2Answer() {
        return 72587986598368L;
    }

    /**
     * An equation of the form ax + by = c.
     */
    record Equation(long a, long b, long c) {

        Equation mult(long factor) {
            return new Equation(a * factor, b * factor, c * factor);
        }

        Equation subtract(Equation other) {
            return  new Equation(a - other.a, b - other.b, c - other.c);
        }

        public long getA(long bans) {
            return (c - b * bans) / a();
        }

        public boolean isValid(long aans, long bans) {
            return c == (a * aans) + (b * bans);
        }
    }

    /**
     * Represents a pair of simultaneous equations.
     */
    record ClawMachine(Equation equation1, Equation equation2) {

        BigVector2 solve() {
            var lcm = lcm(equation1.a(), equation2.a());
            var factor1 = (lcm / equation1.a());
            Equation new1 = equation1.mult(factor1);
            var factor2 = (lcm / equation2.a());
            Equation new2 = equation2.mult(factor2);

            Equation fin = new1.subtract(new2);
            long b = fin.c() / fin.b();
            long a = equation1.getA(b);

            if (equation1.isValid(a, b) && equation2.isValid(a, b)) {
                return new BigVector2(a, b);
            }
            // no integer solution exists
            return new BigVector2(0L, 0L);
        }

        private static long lcm(long number1, long number2) {
            long absHigherNumber = Math.max(number1, number2);
            long absLowerNumber = Math.min(number1, number2);
            long lcm = absHigherNumber;
            while (lcm % absLowerNumber != 0) {
                lcm += absHigherNumber;
            }
            return lcm;
        }

    }
}

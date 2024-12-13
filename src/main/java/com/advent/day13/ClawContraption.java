package com.advent.day13;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.advent.Puzzle;
import com.advent.util.BigVector2;

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
            machines.add(new ClawMachine(new Equation(aVector.x(), bVector.x(), prizeVector.x()),
                    new Equation(aVector.y(), bVector.y(), prizeVector.y())));
            if (it.hasNext()) {
                it.next();
            }
        }
    }

    private static BigVector2 getVector(String line, Pattern pattern) {
        var aMatcher = pattern.matcher(line);
        if (aMatcher.matches()) {
            var ax = Long.parseLong(aMatcher.group(1));
            var ay = Long.parseLong(aMatcher.group(2));
            return new BigVector2(ax, ay);
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
        return 40369L;
    }

    @Override
    public Object computePart2() {
        return machines.stream()
                       .map(machine -> new ClawMachine(new Equation(machine.equation1.a, machine.equation1.b, machine.equation1.ans + 10000000000000L),
                               new Equation(machine.equation2.a, machine.equation2.b, machine.equation2.ans + 10000000000000L)))
                       .map(ClawMachine::findMinTokens).reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part2Answer() {
        return 72587986598368L;
    }

    record Equation(long a, long b, long ans) {

        Equation mult(long factor) {
            return new Equation(a * factor, b * factor, ans * factor);
        }

        Equation minus(Equation other) {
            return  new Equation(a - other.a, b - other.b, ans - other.ans);
        }

        public long getA(long bans) {
            return (ans - b * bans) / a();
        }

        public boolean isValid(long aans, long bans) {
            return ans == (a * aans) + (b * bans);
        }
    }

    record ClawMachine(Equation equation1, Equation equation2) {

        long findMinTokens() {
            var lcm = lcm(equation1.a(), equation2.a());
            var factor1 = (lcm / equation1.a());
            Equation new1 = equation1.mult(factor1);
            var factor2 = (lcm / equation2.a());
            Equation new2 = equation2.mult(factor2);

            Equation fin = new1.minus(new2);
            long b = fin.ans() / fin.b();

            long a = equation1.getA(b);

            if (equation1.isValid(a, b) && equation2.isValid(a, b)) {
                return b + a * 3;
            }
            return 0;
        }

        public static long lcm(long number1, long number2) {
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

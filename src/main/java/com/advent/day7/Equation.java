package com.advent.day7;

import java.util.List;

record Equation(long result, List<Long> numbers) {

    // will return 0 if not solvable
    long solve(boolean shouldConcat) {
        long solution = add(0L, numbers.getFirst(), numbers.subList(1, numbers.size()), shouldConcat);
        if (solution == result) {
            return result;
        }
        solution = mult(0L, numbers.getFirst(), numbers.subList(1, numbers.size()), shouldConcat);
        if (solution == result) {
            return result;
        }
        if (shouldConcat) {
            solution = concat(0L, numbers.getFirst(), numbers.subList(1, numbers.size()));
            if (solution == result) {
                return result;
            }
        }
        return 0L;
    }

    private long mult(long total, Long next, List<Long> remaining, boolean shouldConcat) {
        total = total * next;
        return recur(total, remaining, shouldConcat);
    }

    private long add(long total, Long next, List<Long> remaining, boolean shouldConcat) {
        total = total + next;
        return recur(total, remaining, shouldConcat);
    }

    private long concat(long total, Long next, List<Long> remaining) {
        total = Long.parseLong(total + String.valueOf(next));
        return recur(total, remaining, true);
    }

    private long recur(long total, List<Long> remaining, boolean shouldConcat) {
        if (remaining.isEmpty()) {
            return total;
        }
        long solution = add(total, remaining.getFirst(), remaining.subList(1, remaining.size()), shouldConcat);
        if (solution == result) {
            return solution;
        }
        solution = mult(total, remaining.getFirst(), remaining.subList(1, remaining.size()), shouldConcat);
        if (solution == result) {
            return result;
        }
        if (shouldConcat) {
            solution = concat(total, remaining.getFirst(), remaining.subList(1, remaining.size()));
            if (solution == result) {
                return result;
            }
        }
        return 0;
    }
}

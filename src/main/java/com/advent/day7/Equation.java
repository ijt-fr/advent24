package com.advent.day7;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

record Equation(long solution, List<Long> numbers) {

    // will return 0 if not solvable
    long solve(Set<Operation> operations) {
        return operations.stream()
                       .map(op -> recur(0L, numbers, op, operations))
                       .filter(s -> s.equals(solution))
                       .findAny().orElse(0L);
    }

    private long recur(long currentTotal, List<Long> remaining, Operation currentOp, Set<Operation> operations) {
        if (currentTotal > solution || remaining.isEmpty()) {
            return currentTotal;
        }
        var newTotal = currentOp.apply(currentTotal, remaining.getFirst());
        return operations.stream()
                       .map(op -> recur(newTotal, remaining.subList(1, remaining.size()), op, operations))
                       .filter(s -> s.equals(solution))
                       .findAny().orElse(0L);
    }

    enum Operation {

        ADD(Long::sum),
        MULT((a, b) -> a * b),
        CONCAT((a, b) -> Long.parseLong(String.format("%d%d", a, b)));

        private final BiFunction<Long, Long, Long> operation;

        Operation(BiFunction<Long, Long, Long> operation) {
            this.operation = operation;
        }

        private long apply(long a, long b) {
            return operation.apply(a, b);
        }
    }
}

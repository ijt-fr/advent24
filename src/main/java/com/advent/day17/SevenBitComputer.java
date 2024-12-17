package com.advent.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.advent.day17.memoization.Adv;
import com.advent.day17.memoization.Bst;
import com.advent.day17.memoization.Bxc;
import com.advent.day17.memoization.Bxl;

public class SevenBitComputer {

    private static final Adv ADV = new Adv();
    private static final Bxl BXL = new Bxl();
    private static final Bst BST = new Bst();
    private static final Bxc BXC = new Bxc();
    long a;
    long b;
    long c;

    int[] instructions;
    int instructionPointer = 0;

    List<Long> output = new ArrayList<>();

    public SevenBitComputer(long a, long b, long c, int[] instructions) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.instructions = instructions;
    }

    public List<Long> run() {
        while (instructionPointer < instructions.length) {
            int opcode = instructions[instructionPointer];
            int operand = instructions[instructionPointer + 1];
            executeInstruction(opcode, operand);
            instructionPointer += 2;
        }
        return output;
    }

    public long next() {
        var currentOutputs = output.size();
        while (output.size() == currentOutputs) {
            int opcode = instructions[instructionPointer];
            int operand = instructions[instructionPointer + 1];
            executeInstruction(opcode, operand);
            instructionPointer += 2;
        }
        return output.getLast();
    }

    public void executeInstruction(int opcode, int operand) {
        try {
            switch (opcode) {
                case 0 -> adv(operand);
                case 1 -> bxl(operand);
                case 2 -> bst(operand);
                case 3 -> jnz(operand);
                case 4 -> bxc(operand);
                case 5 -> out(operand);
                case 6 -> bdv(operand);
                case 7 -> cdv(operand);
                default -> throw new IllegalStateException("Invalid opcode: " + opcode);
            }
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    private void out(int operand) throws ExecutionException {
        output.add(BST.get(combo(operand)));
    }

    private void bxc(long ignored) throws ExecutionException {
        b = BXC.get(b, c);
    }

    private void jnz(int operand) {
        if (a != 0) {
            instructionPointer = operand - 2;
        }
    }

    private void bst(int operand) throws ExecutionException {
        b = BST.get(combo(operand));
    }

    private void bxl(int operand) throws ExecutionException {
        b = BXL.get(b, operand);
    }

    private void adv(int operand) throws ExecutionException {
        a = ADV.get(a, combo(operand));
    }

    private void bdv(int operand) throws ExecutionException {
        b = ADV.get(a, combo(operand));
    }

    private void cdv(int operand) throws ExecutionException {
        c = ADV.get(a, combo(operand));
    }

    private Long combo(int operand) {
        return switch (operand) {
            case 0, 1, 3 -> (long) operand;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> throw new IllegalArgumentException("Invalid combo operand: " + operand);
        };
    }

    public record Combo(long val) {

        public Combo(int operand, long a, long b, long c) {
            this(switch (operand) {
                case 0, 1, 3 -> operand;
                case 4 -> a;
                case 5 -> b;
                case 6 -> c;
                default -> throw new IllegalArgumentException("Invalid combo operand: " + operand);
            });
        }
    }
}

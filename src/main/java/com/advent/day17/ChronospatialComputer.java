package com.advent.day17;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.advent.Puzzle;

public class ChronospatialComputer extends Puzzle {
    private long a;
    private long b;
    private long c;
    private int[] instructions;

    @Override
    public void parseInput(List<String> lines) {
        a = Long.parseLong(StringUtils.substringAfter(lines.get(0), "Register A: "));
        b = Long.parseLong(StringUtils.substringAfter(lines.get(1), "Register B: "));
        c = Long.parseLong(StringUtils.substringAfter(lines.get(2), "Register C: "));
        instructions = Arrays.stream(StringUtils.substringAfter(lines.get(4), "Program: ").split(","))
                .mapToInt(Integer::parseInt).toArray();
    }

    @Override
    public Object computePart1() {
        SevenBitComputer computer = new SevenBitComputer(a, b, c, instructions);
        return computer.run().stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public Object part1Answer() {
        return "2,7,6,5,6,0,2,3,1";
    }

    @Override
    public Object computePart2() {
        List<Long> instructionsList = Arrays.stream(instructions).boxed().map(i -> (long) i).toList();
//        a = 35_151_358_933_097L;
        // 16 digits starts at 35,184,372,088,832 with last digit 2
        // 70,368,744,177,664 -> last digit 1
        // 105553116266496 -> last digit 0
        //
        long result = 0L; // first 16 result number

        long startA = 1;
        for (int i = instructionsList.size() - 1; i >= 0; i--) {
            a = startA;
            long currentDigits = 1L << (3 * i);
            result += currentDigits;
            Long something;
            do {
                a++;
                result += currentDigits;
                var resultList = new SevenBitComputer(a, b, c, instructions).run();
                something = resultList.getFirst();
            } while (!something.equals(instructionsList.get(i)));
            startA <<= 3;
        }
        System.out.println(new SevenBitComputer(result, b, c, instructions).run());
        return result - 117440L;

//        a = result;
//        boolean isMatch = false;
//        while (!isMatch) {
//            isMatch = true;
//            var computer = new SevenBitComputer(a, b, c, instructions);
//            var out = computer.run();
//            System.out.println(a + ": " + out);
//            if (!out.equals(instructionsList)) {
//                isMatch = false;
//            }
//            for (Long instruct : instructionsList) {
//                long next = computer.next();
//                if (next != instruct) {
//                    isMatch = false;
//                    break;
//                }
//            }
//        }
//        return a;
    }

    @Override
    public Object part2Answer() {
        return null;
    }

}

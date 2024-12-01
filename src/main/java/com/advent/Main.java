package com.advent;

import com.advent.day1.HistorianHysteria2;

public class Main {

    public static void main(String[]  args) {
        Puzzle puzzle = new HistorianHysteria2();
        try {
            System.out.println(puzzle.computeAnswer(puzzle.readInput()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

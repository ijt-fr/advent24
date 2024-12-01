package com.advent.day1;

import java.util.ArrayList;
import java.util.List;

import com.advent.Puzzle;

public class HistorianHysteria1 extends Puzzle {

    List<Integer> list1;
    List<Integer> list2;

    HistorianHysteria1() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
    }

    @Override
    public Long computeAnswer(List<String> input) {
        consumeInputs(input);
        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);

        long totalDistance = 0L;
        for (int i = 0; i < list1.size(); i++) {
            Integer num1 = list1.get(i);
            Integer num2 = list2.get(i);
            totalDistance+=Math.abs(num1 - num2);
        }
        return totalDistance;
    }

    protected void consumeInputs(List<String> input) {
        input.forEach(s -> {
            var splits = s.split(" +");
            list1.add(Integer.parseInt(splits[0]));
            list2.add(Integer.parseInt(splits[1]));
        });
    }
}

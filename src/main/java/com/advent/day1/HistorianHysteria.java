package com.advent.day1;

import java.util.ArrayList;
import java.util.List;

import com.advent.Puzzle;

public class HistorianHysteria extends Puzzle {

    List<Integer> list1;
    List<Integer> list2;

    HistorianHysteria() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
    }

    @Override
    public Long computePart1(List<String> input) {
        consumeInputs(input);
        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);

        long totalDistance = 0L;
        for (int i = 0; i < list1.size(); i++) {
            totalDistance += Math.abs(list1.get(i) - list2.get(i));
        }
        return totalDistance;
    }

    @Override
    public Object computePart2(List<String> input) {
        consumeInputs(input);
        long totalSimilarity = 0L;
        for (Integer num1 : list1) {
            totalSimilarity += num1 * list2.stream().filter(num1::equals).count();
        }
        return totalSimilarity;
    }

    private void consumeInputs(List<String> input) {
        input.forEach(s -> {
            var splits = s.split(" +");
            list1.add(Integer.parseInt(splits[0]));
            list2.add(Integer.parseInt(splits[1]));
        });
    }
}
package com.advent.day1;

import java.util.ArrayList;
import java.util.List;

import com.advent.Puzzle;

public class HistorianHysteria extends Puzzle {

    List<Integer> list1;
    List<Integer> list2;

    @Override
    public Long computePart1() {
        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);

        long totalDistance = 0L;
        for (int i = 0; i < list1.size(); i++) {
            totalDistance += Math.abs(list1.get(i) - list2.get(i));
        }
        return totalDistance;
    }

    @Override
    public Object computePart2() {
        long totalSimilarity = 0L;
        for (Integer num1 : list1) {
            totalSimilarity += num1 * list2.stream().filter(num1::equals).count();
        }
        return totalSimilarity;
    }

    @Override
    public void parseInput(List<String> input) {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        input.forEach(s -> {
            var splits = s.split(" +");
            list1.add(Integer.parseInt(splits[0]));
            list2.add(Integer.parseInt(splits[1]));
        });
    }

    @Override
    public Object part1Answer() {
        return 2742123L;
    }

    @Override
    public Object part2Answer() {
        return 21328497L;
    }
}

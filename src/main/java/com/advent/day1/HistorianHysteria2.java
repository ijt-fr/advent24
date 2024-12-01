package com.advent.day1;

import java.util.ArrayList;
import java.util.List;

import com.advent.Puzzle;


public class HistorianHysteria2 extends Puzzle {

    @Override
    public Long computeAnswer(List<String> input) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        input.forEach(s -> {
            var splits = s.split(" +");
            list1.add(Integer.parseInt(splits[0]));
            list2.add(Integer.parseInt(splits[1]));
        });


        long totalSimilarity = 0L;
        for (Integer num1 : list1) {
            totalSimilarity+=num1 * list2.stream().filter(num1::equals).count();
        }
        return totalSimilarity;
    }
}

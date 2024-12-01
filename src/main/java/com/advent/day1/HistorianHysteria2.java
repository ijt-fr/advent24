package com.advent.day1;

import java.util.List;


public class HistorianHysteria2 extends HistorianHysteria1 {

    @Override
    public Long computeAnswer(List<String> input) {
        consumeInputs(input);
        long totalSimilarity = 0L;
        for (Integer num1 : list1) {
            totalSimilarity+=num1 * list2.stream().filter(num1::equals).count();
        }
        return totalSimilarity;
    }
}

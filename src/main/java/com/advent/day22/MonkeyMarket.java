package com.advent.day22;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.advent.Puzzle;

public class MonkeyMarket extends Puzzle {
    private Stream<Long> secretNumbers;

    @Override
    public void parseInput(List<String> lines) {
        secretNumbers = lines.stream().map(Long::parseLong);
    }

    @Override
    public Object computePart1() {
        for (int i = 0; i < 2000; i++) {
            secretNumbers = secretNumbers.map(this::next);
        }
        return secretNumbers.reduce(Long::sum).orElseThrow();
    }

    public long next(long n) {
        long one = prune(mix(n << 6, n));
        long two = prune(mix(one >> 5, one));
        return prune(mix(two << 11, two));
    }

    private long prune(long n) {
        return n % 16_777_216;
    }

    private long mix(long a, long b) {
        return a ^ b;
    }

    @Override
    public Object part1Answer() {
        return 16953639210L;
    }

    @Override
    public Object computePart2() {
        List<Map<PreviousDiffs, Integer>> myMap = secretNumbers.map(secret -> getPreviousDiffsIntegerMap(secret, 2000)).toList();
        return myMap.stream().reduce((map1, map2) -> {
                           map2.forEach((key, value) -> {
                               map1.compute(key, (k, v) -> {
                                   if (v == null) {
                                     return  value;
                                   }
                                   return v + value;
                               });
                           });
                           return map1;
                       }).map(map -> map.entrySet().stream()
                                                                    .max(Map.Entry.comparingByValue())
                                                                    .orElseThrow())
                       .orElseThrow()
                       .getValue();
    }

    public Map<PreviousDiffs, Integer> getPreviousDiffsIntegerMap(Long secret, int count) {
        List<Integer> prices = new ArrayList<>();
        long current = secret;
        prices.add(getLastDigit(current));
        for (int i = 0; i < count; i++) {
            current = next(current);
            prices.add(getLastDigit(current));
        }
        Map<PreviousDiffs, Integer> map = new LinkedHashMap<>();
        for (int i = 4; i < prices.size(); i++) {
            map.putIfAbsent(new PreviousDiffs(
                    prices.get(i - 3) - prices.get(i - 4),
                    prices.get(i - 2) - prices.get(i - 3),
                    prices.get(i - 1) - prices.get(i - 2),
                    prices.get(i) - prices.get(i - 1)), prices.get(i));
        }
        return map;
    }

    private static int getLastDigit(long current) {
        String str = Long.toString(current);
        return Integer.parseInt(str.substring(str.length() - 1));
    }

    @Override
    public Object part2Answer() {
        return 1863;
    }

    public record PreviousDiffs(int diff1, int diff2, int diff3, int diff4) {
    }
}

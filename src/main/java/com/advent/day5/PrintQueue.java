package com.advent.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.advent.Puzzle;

public class PrintQueue extends Puzzle {

    private Map<Integer, Set<Integer>> pageOrderingRules;
    private List<List<Integer>> updates;

    @Override
    public void parseInput(List<String> input) {
        var it = input.iterator();
        pageOrderingRules = parsePageOrderingRules(it);
        updates = parseUpdates(it);
    }

    @Override
    public Object computePart1() {
        long middlePages = 0L;
        for (List<Integer> update : updates) {
            if (isValid(update)) {
                middlePages += getMiddleValue(update);
            }
        }
        return middlePages;
    }

    @Override
    public Object part1Answer() {
        return 6612L;
    }

    @Override
    public Object computePart2() {
        long middleValues = 0L;
        for (List<Integer> update : updates) {
            if (!isValid(update)) {
                for (Integer page : update) {
                    int mustGoAfterCount = (int) pageOrderingRules.entrySet().stream()
                                                         .filter(e -> update.contains(e.getKey()))
                                                         .filter(e -> e.getValue().contains(page))
                                                         .count();
                    if (isMiddle(update, mustGoAfterCount)) {
                        middleValues += page;
                        break;
                    }
                }
            }
        }
        return middleValues;
    }

    @Override
    public Object part2Answer() {
        return 4944L;
    }

    private boolean isMiddle(List<Integer> update, int mustGoAfterCount) {
        return mustGoAfterCount == (update.size() - 1) / 2;
    }

    private boolean isValid(List<Integer> update) {
        for (var before : pageOrderingRules.keySet()) {
            int beforeIndex = update.indexOf(before);
            if (beforeIndex == -1) {
                continue;
            }
            for (Integer after : pageOrderingRules.get(before)) {
                int afterIndex = update.indexOf(after);
                if (afterIndex != -1 && beforeIndex > afterIndex) {
                    return false;
                }
            }
        }
        return true;
    }

    private long getMiddleValue(List<Integer> update) {
        return update.get((update.size() - 1) / 2);
    }

    private static List<List<Integer>> parseUpdates(Iterator<String> it) {
        List<List<Integer>> updates = new ArrayList<>();
        while(it.hasNext()) {
            var line = it.next();
            updates.add(Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
        }
        return updates;
    }

    private static Map<Integer, Set<Integer>> parsePageOrderingRules(Iterator<String> it) {
        Map<Integer, Set<Integer>> pageOrderingRules = new HashMap<>();
        while (true) {
            var line = it.next();
            if (line.isBlank()) {
                break;
            }
            String[] vals = line.split("\\|");
            pageOrderingRules.compute(Integer.valueOf(vals[0]), (k, v) -> {
                if (v == null) {
                    v =  new HashSet<>();
                }
                v.add(Integer.valueOf(vals[1]));
                return v;
            });
        }
        return pageOrderingRules;
    }
}

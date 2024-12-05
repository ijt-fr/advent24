package com.advent.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.advent.Puzzle;

public class PrintQueue extends Puzzle {

    Map<Integer, Set<Integer>> pageOrderingRules;

    @Override
    public Object computePart1(List<String> input) {
        var it = input.iterator();
        pageOrderingRules = parsePageOrderingRules(it);
        List<List<Integer>> updates = parseUpdates(it);

        long middlePages = 0L;
        for (List<Integer> update : updates) {
            if (isValid(update)) {
                middlePages += getMiddleValue(update);
            }
        }
        return middlePages;
    }

    @Override
    public Object computePart2(List<String> input) {
        var it = input.iterator();
        pageOrderingRules = parsePageOrderingRules(it);
        List<List<Integer>> updates = parseUpdates(it);



        long middleValues = 0L;
        for (List<Integer> update : updates) {
            if (!isValid(update)) {
                while (!isValid(update)) {
                    Collections.shuffle(update);
                }
                middleValues += getMiddleValue(update);
            }
//            for (var before : pageOrderingRules.keySet()) {
//                int beforeIndex = update.indexOf(before);
//                if (beforeIndex == -1) {
//                    continue;
//                }
//                Optional<Integer> firstIndex = pageOrderingRules.get(before).stream().map(update::indexOf)
//                        .filter(index -> index != -1).reduce(Math::min);
//                firstIndex.ifPresent(integer -> {
//                    update.remove(beforeIndex);
//                    update.add(integer, before);
//                });
//            }
//            middleValues += update.get((update.size() - 1) / 2);
        }


        return middleValues;
    }

    private boolean isValid(List<Integer> update) {
        boolean isInCorrectOrder = true;
        for (var before : pageOrderingRules.keySet()) {
            int beforeIndex = update.indexOf(before);
            if (beforeIndex == -1) {
                continue;
            }
            for (Integer after : pageOrderingRules.get(before)) {
                int afterIndex = update.indexOf(after);
                if (afterIndex != -1 && beforeIndex > afterIndex) {
                    isInCorrectOrder = false;
                }
            }
        }
        return isInCorrectOrder;
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

    private long getMiddleValue(List<Integer> update) {
        return update.get((update.size() - 1) / 2);
    }
}

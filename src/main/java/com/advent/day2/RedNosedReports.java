package com.advent.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.advent.Puzzle;

public class RedNosedReports extends Puzzle {
    @Override
    public Object computePart1(List<String> input) {
        return input.stream()
                       .map(RedNosedReports::parseReport)
                       .filter(RedNosedReports::isSafe).count();
    }

    @Override
    public Object computePart2(List<String> input) {
        Map<List<Integer>, Boolean> reports = input.stream()
                .map(RedNosedReports::parseReport)
                .collect(Collectors.toMap(report -> report, RedNosedReports::isSafe));

        long safeUnsafeReportCount = reports.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .filter(report -> {
                    for (int i = 0; i < report.size(); i++) {
                        List<Integer> subReport = new ArrayList<>(report);
                        //noinspection SuspiciousListRemoveInLoop
                        subReport.remove(i);
                        if (isSafe(subReport)) {
                            return true;
                        }
                    }
                    return false;
                }).count();

        return reports.values().stream().filter(safe -> safe).count() + safeUnsafeReportCount;
    }

    private static boolean isSafe(List<Integer> report) {
        Integer first = report.get(0);
        Integer second = report.get(1);
        boolean increasing = second > first;
        for (int i = 0; i < report.size() - 1; i++) {
            var current = report.get(i);
            var next = report.get(i + 1);
            var diff = next - current;
            var mod = Math.abs(diff);
            if (mod < 1 || mod > 3 || (increasing && diff < 0) || (!increasing && diff > 0)) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> parseReport(String rawReport) {
        return Arrays.stream(rawReport.split(" "))
                       .map(Integer::parseInt)
                       .toList();
    }
}

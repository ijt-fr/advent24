package com.advent.day3;

import static java.lang.Long.parseLong;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.substringAfter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.advent.Puzzle;

public class MullItOver extends Puzzle {

    private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    private String joinedInput;

    @Override
    public Object computePart1() {
        Matcher matcher = MUL_PATTERN.matcher(joinedInput);
        long total = 0L;
        while (matcher.find()) {
            total += matchGroup(matcher);
        }
        return total;
    }

    @Override
    public Object computePart2() {
        String[] regions = ("do()" + joinedInput).split("don't\\(\\)");
        String doRegions = Arrays.stream(regions)
                              .map(reg -> substringAfter(reg, "do()"))
                              .collect(joining("J"));
        Matcher matcher = MUL_PATTERN.matcher(doRegions);
        long total = 0L;
        while (matcher.find()) {
            total += matchGroup(matcher);
        }
        return total;
    }

    @Override
    public void parseInput(List<String> lines) {
        joinedInput = join("J", lines);
    }

    @Override
    public Object part1Answer() {
        return 184576302L;
    }

    @Override
    public Object part2Answer() {
        return 118173507L;
    }

    private static long matchGroup(Matcher matcher) {
        var val1 = parseLong(matcher.group(1));
        var val2 = parseLong(matcher.group(2));
        return val1 * val2;
    }
}

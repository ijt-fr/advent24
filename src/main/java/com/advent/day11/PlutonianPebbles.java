package com.advent.day11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import com.advent.Puzzle;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class PlutonianPebbles extends Puzzle {

    Stream<Long> stones;
    LoadingCache<CacheKey, Long> memoCache = CacheBuilder.newBuilder()
                                                     .build(CacheLoader.from(this::blink));

    @Override
    public void parseInput(List<String> lines) {
        stones = Arrays.stream(lines.getFirst().split(" "))
                .map(Long::parseLong);
    }

    @Override
    public Object computePart1() {
        return stones.map(l -> blink(l, 25)).reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part1Answer() {
        return 231278L;
    }

    @Override
    public Object computePart2() {
        return stones.map(l -> blink(l, 75)).reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part2Answer() {
        return 274229228071551L;
    }

    private Long blink(Long l, int count) {
        try {
            return memoCache.get(new CacheKey(l, count));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private long blink(CacheKey key) {
        int newCount = key.count() - 1;
        long number = key.number();
        if (newCount == 0) {
            if (number == 0) {
                return 1;
            }
            if (digits(number) % 2 == 0) {
                return 2;
            }
            return 1;
        }
        try {
            if (number == 0) {
                return memoCache.get(new CacheKey(1L, newCount));
            }
            var digits = digits(number);
            if (digits % 2 == 0) {
                long multiple = (long) Math.pow(10, (double) digits / 2);
                return memoCache.get(new CacheKey(upper(number, multiple), newCount))
                               + memoCache.get(new CacheKey(lower(number, multiple), newCount));
            }
            return memoCache.get(new CacheKey(number * 2024L, newCount));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private int digits(long number) {
        return (int) (Math.log10(number) + 1);
    }

    private long upper(long number, long multiple) {
        return number / multiple;
    }

    private long lower(long number, long multiple) {
        return number % multiple;
    }

    record CacheKey(Long number, int count) {

    }
}

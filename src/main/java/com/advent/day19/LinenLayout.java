package com.advent.day19;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.advent.Puzzle;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class LinenLayout extends Puzzle {

    private final LoadingCache<String, Long> cache = CacheBuilder.newBuilder()
                                                       .build(new MatchesCacheLoader());

    private List<String> towelPatterns;
    private List<String> desiredDesigns;
    private Pattern pattern;

    @Override
    public void parseInput(List<String> lines) {
        towelPatterns = Arrays.stream(lines.getFirst().split(", ")).toList();
        desiredDesigns = lines.subList(2, lines.size());
        pattern = Pattern.compile( "(" + String.join("|", towelPatterns) + ")+");
    }

    @Override
    public Object computePart1() {
        return desiredDesigns.stream().filter(this::isPossible)
                .count();
    }

    private boolean isPossible(String design) {
        return pattern.matcher(design).matches();
    }

    @Override
    public Object part1Answer() {
        return 247L;
    }

    @Override
    public Object computePart2() {
        return desiredDesigns.stream().filter(this::isPossible)
                       .map(this::countMatches)
                       .reduce(Long::sum).orElse(0L);
    }

    private long countMatches(String design) {
        try {
            return cache.get(design);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object part2Answer() {
        return 692596560138745L;
    }

    private class MatchesCacheLoader extends CacheLoader<String, Long> {
        @Override
        public Long load(String design) {
            if (design.isEmpty()) {
                return 1L;
            }
            return towelPatterns.stream()
                           .filter(pattern -> pattern.length() <= design.length())
                           .filter(design::startsWith)
                           .flatMap(pattern -> {
                               try {
                                   return Stream.of(cache.get(design.substring(pattern.length())));
                               } catch (ExecutionException e) {
                                   throw new RuntimeException(e);
                               }
                           }).reduce(Long::sum).orElse(0L);
        }
    }
}

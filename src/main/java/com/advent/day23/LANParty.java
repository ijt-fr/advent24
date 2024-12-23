package com.advent.day23;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.advent.Puzzle;
import com.google.common.collect.Sets;

public class LANParty extends Puzzle {

    Set<Computer> tComputers;
    Set<Set<Computer>> computersGroups;

    @Override
    public void parseInput(List<String> lines) {
        tComputers = new HashSet<>();
        computersGroups = new HashSet<>();
        lines.forEach(line -> {
            String[] parts = line.split("-");
            Computer computer1 = new Computer(parts[0]);
            Computer computer2 = new Computer(parts[1]);
            computersGroups.add(Set.of(computer1, computer2));
            if (computer1.code().startsWith("t")) {
                tComputers.add(computer1);
            }
            if (computer2.code().startsWith("t"))
                tComputers.add(computer2);
        });
    }

    @Override
    public Object computePart1() {
        return computersGroups.stream().flatMap(this::findIntersections)
                       .filter(s -> s.stream().anyMatch(computer -> computer.code().startsWith("t")))
                       .distinct().count();
    }

    private Stream<Set<Computer>> findIntersections(Set<Computer> group) {
        Set<Computer> computersThatMatchAll = group.stream()
                                                      .map(this::getLinks)
                                                      .reduce(Sets::intersection)
                                                      .orElseThrow();
        return computersThatMatchAll.stream().map(c -> {
            Set<Computer> set = new HashSet<>(group);
            set.add(c);
            return set;
        });
    }

    private Set<Computer> getLinks(Computer computer) {
        return computersGroups.stream()
                       .filter(g -> g.contains(computer))
                       .map(g -> Sets.difference(g, Set.of(computer)).stream().findAny().orElseThrow())
                       .collect(Collectors.toSet());
    }

    @Override
    public Object part1Answer() {
        return 1302L;
    }

    @Override
    public Object computePart2() {
        Set<Set<Computer>> firstSet = computersGroups.stream()
                       .flatMap(this::findIntersections)
                       .collect(Collectors.toSet());
        Set<Set<Computer>> previousSet = null;
        while (!firstSet.isEmpty()) {
            previousSet = firstSet;
            firstSet = firstSet.stream().flatMap(this::findIntersections)
                    .collect(Collectors.toSet());
        }
        if (previousSet == null || previousSet.size() != 1) {
            throw new IllegalStateException("somethings wrong");
        }
        return previousSet.stream().findAny().orElseThrow().stream().map(Computer::code).sorted()
                       .collect(Collectors.joining(","));
    }

    @Override
    public Object part2Answer() {
        return "cb,df,fo,ho,kk,nw,ox,pq,rt,sf,tq,wi,xz";
    }
}

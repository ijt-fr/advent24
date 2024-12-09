package com.advent;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public abstract class Puzzle {

    private List<String> readInput() throws IOException {
        var filename = getClass().getSimpleName().toLowerCase() + ".input";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        return FileUtils.readLines(file, "UTF-8");
    }

    public abstract void parseInput(List<String> lines);

    public abstract Object computePart1();

    public abstract Object part1Answer();

    public abstract Object computePart2();

    public abstract Object part2Answer();

    @Test
    public void part1() throws IOException {
        parseInput(readInput());
        Object answer = computePart1();
        System.out.println(answer);
        assertThat(answer).isEqualTo(part1Answer());
    }

    @Test
    public void part2() throws IOException {
        parseInput(readInput());
        Object answer = computePart2();
        System.out.println(answer);
        assertThat(answer).isEqualTo(part2Answer());
    }

}

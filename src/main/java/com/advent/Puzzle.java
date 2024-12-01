package com.advent;

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

    public abstract Object computePart1(List<String> input);

    public abstract Object computePart2(List<String> input);

    @Test
    public void part1() throws IOException {
        System.out.println(computePart1(readInput()));
    }

    @Test
    public void part2() throws IOException {
        System.out.println(computePart2(readInput()));
    }

}

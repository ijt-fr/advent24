package com.advent;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public abstract class Puzzle {

    public List<String> readInput() throws IOException {
        return readInput(getClass().getSimpleName().toLowerCase() + ".input");
    }

    protected List<String> readInput(String filename) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        return FileUtils.readLines(file, "UTF-8");
    }

    public abstract Object computeAnswer(List<String> input);

    public char[][] toCharMatrix(List<String> lines) {
        return lines.stream().map(String::toCharArray).toArray(char[][]::new);
    }

    @Test
    public void test() throws IOException {
        System.out.println(computeAnswer(readInput()));
    }

}

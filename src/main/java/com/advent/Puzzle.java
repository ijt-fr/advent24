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

    public abstract Object computeAnswer(List<String> input);

    @Test
    public void test() throws IOException {
        System.out.println(computeAnswer(readInput()));
    }

}

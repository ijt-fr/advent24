package com.advent.util;

import java.util.List;

public class InputUtils {

    public static char[][] toCharMatrix(List<String> lines) {
        return lines.stream().map(String::toCharArray).toArray(char[][]::new);
    }
}

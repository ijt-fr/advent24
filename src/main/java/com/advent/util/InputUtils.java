package com.advent.util;

import java.util.Arrays;
import java.util.List;

public class InputUtils {

    public static char[][] toCharMatrix(List<String> lines) {
        return lines.stream().map(String::toCharArray).toArray(char[][]::new);
    }

    public static Integer[][] toIntMatrix(List<String> lines) {
        return lines.stream()
                       .map(str -> Arrays.stream(str.split("")).map(Integer::valueOf)
                                           .toArray(Integer[]::new))
                       .toArray(Integer[][]::new);
    }

    public static String toString(char[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                sb.append(matrix[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

package com.advent.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class InputUtils {

    public static Character[][] toCharMatrix(List<String> lines) {
        return lines.stream()
                       .map(String::toCharArray)
                       .map(ArrayUtils::toObject)
                       .toArray(Character[][]::new);
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

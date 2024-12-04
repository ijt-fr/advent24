package com.advent.day4;

import java.util.ArrayList;
import java.util.List;

import com.advent.Puzzle;

public class CeresSearch extends Puzzle {

    @Override
    public Object computePart1(List<String> input) {
        char[][] matrix = toCharMatrix(input);
        List<Node> xNodes = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                char c = matrix[i][j];
                if (c == 'X') {
                    xNodes.add(new Node(j, i));
                }
            }
        }
        Long xmasCount = 0L;
        for (Node node : xNodes) {
            for (int i = node.y - 1; i <= node.y + 1; i++) {
                if (isOutOfYBounds(i, matrix)) continue;
                for (int j = node.x - 1; j <= node.x + 1; j++) {
                    if (isOutOfXBounds(j, matrix)) continue;
                    char c = matrix[i][j];
                    if (c == 'M') {
                        int aY = node.y + 2 *  (i - node.y);
                        if (isOutOfYBounds(aY, matrix)) continue;
                        int aX = node.x + 2 * (j - node.x);
                        if (isOutOfXBounds(aX, matrix)) continue;
                        if (matrix[aY][aX] == 'A') {
                            int sY = node.y + 3 * (i - node.y);
                            if (isOutOfYBounds(sY, matrix)) {
                                continue;
                            }
                            int sX = node.x + 3 * (j - node.x);
                            if (isOutOfXBounds(sX, matrix)) continue;
                            if (matrix[sY][sX] == 'S') {
                                xmasCount++;
                            }
                        }
                    }
                }
            }
        }
        return xmasCount;
    }

    private static boolean isOutOfXBounds(int x, char[][] matrix) {
        if (x < 0 || x >= matrix[0].length) {
            return true;
        }
        return false;
    }

    private static boolean isOutOfYBounds(int y, char[][] matrix) {
        return y < 0 || y >= matrix.length;
    }

    private char[][] toCharMatrix(List<String> lines) {
        return lines.stream().map(String::toCharArray).toArray(char[][]::new);
    }

    @Override
    public Object computePart2(List<String> input) {
        char[][] matrix = toCharMatrix(input);
        List<Node> aNodes = new ArrayList<>();

        for (int i = 1; i < matrix.length - 1; i++) {
            for (int j = 1; j < matrix[0].length - 1; j++) {
                char c = matrix[i][j];
                if (c == 'A') {
                    aNodes.add(new Node(j, i));
                }
            }
        }

        long xmasCount = 0L;
        for (Node node : aNodes) {
            char upLeft = matrix[node.y - 1][node.x - 1];
            char upRight = matrix[node.y + 1][node.x - 1];
            char downLeft = matrix[node.y - 1][node.x + 1];
            char downRight = matrix[node.y + 1][node.x + 1];

            if (upLeft == 'M' && downRight == 'S') {
                if (upRight == 'M' && downLeft == 'S') {
                    xmasCount++;
                } else if (upRight == 'S' && downLeft == 'M') {
                    xmasCount++;
                }
            } else if (upLeft == 'S' && downRight == 'M') {
                if (upRight == 'M' && downLeft == 'S') {
                    xmasCount++;
                } else if (upRight == 'S' && downLeft == 'M') {
                    xmasCount++;
                }
            }
        }

        return xmasCount;
    }

}

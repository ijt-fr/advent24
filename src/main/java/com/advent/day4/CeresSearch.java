package com.advent.day4;

import java.util.List;

import com.advent.Puzzle;
import com.advent.util.InputUtils;

public class CeresSearch extends Puzzle {

    private Character[][] matrix;

    @Override
    public Object computePart1() {
        Long xmasCount = 0L;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                char c = matrix[i][j];
                if (c == 'X') {
                    for (int k = i - 1; k <= i + 1; k++) {
                        if (isOutOfYBounds(k, matrix)) continue;
                        for (int l = j - 1; l <= j + 1; l++) {
                            if (isOutOfXBounds(l, matrix)) continue;
                            char d = matrix[k][l];
                            if (d == 'M') {
                                int aY = i + 2 *  (k - i);
                                if (isOutOfYBounds(aY, matrix)) continue;
                                int aX = j + 2 * (l - j);
                                if (isOutOfXBounds(aX, matrix)) continue;
                                if (matrix[aY][aX] == 'A') {
                                    int sY = i + 3 * (k - i);
                                    if (isOutOfYBounds(sY, matrix)) continue;
                                    int sX = j + 3 * (l - j);
                                    if (isOutOfXBounds(sX, matrix)) continue;
                                    if (matrix[sY][sX] == 'S') {
                                        xmasCount++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return xmasCount;
    }

    private static boolean isOutOfXBounds(int x, Character[][] matrix) {
        if (x < 0 || x >= matrix[0].length) {
            return true;
        }
        return false;
    }

    private static boolean isOutOfYBounds(int y, Character[][] matrix) {
        return y < 0 || y >= matrix.length;
    }

    @Override
    public Object computePart2() {
        long xmasCount = 0L;
        for (int i = 1; i < matrix.length - 1; i++) {
            for (int j = 1; j < matrix[0].length - 1; j++) {
                char c = matrix[i][j];
                if (c == 'A') {
                    char upLeft = matrix[i - 1][j - 1];
                    char upRight = matrix[i + 1][j - 1];
                    char downLeft = matrix[i - 1][j + 1];
                    char downRight = matrix[i + 1][j + 1];

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
            }
        }
        return xmasCount;
    }

    @Override
    public void parseInput(List<String> lines) {
        matrix = InputUtils.toCharMatrix(lines);
    }

    @Override
    public Object part1Answer() {
        return 2397L;
    }

    @Override
    public Object part2Answer() {
        return 1824L;
    }

}

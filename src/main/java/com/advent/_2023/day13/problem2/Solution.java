package com.advent._2023.day13.problem2;

import com.advent._2023.Parser;

import java.util.List;

public class Solution {
    public long solution(Parser parser) {
        List<String> list = parser.streamLines().toList();
        int result = 0;
        int idx = list.indexOf("");
        while (idx != -1) {
            result += solveProblem(list.subList(0, idx));
            list = list.subList(idx + 1, list.size());
            idx = list.indexOf("");
        }
        result += solveProblem(list);
        return result;
    }

    private int solveProblem(List<String> strings) {
        char[][] data = strings.stream().map(String::toCharArray).toArray(char[][]::new);
        int result = checkRows(transpose(data));
        if (result == -1) {
            result = checkRows(data) * 100;
        }
        return result;
    }


    private int checkRows(char[][] data) {
        for (int i = 0; i < data.length - 1; i++) {
            Result compare = compare(data[i], data[i + 1]);
            if (compare == Result.EQUALS || compare == Result.SMUDGE) {
                if (isMirror(data, i, compare == Result.EQUALS)) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    private Result compare(char[] x, char[] y) {
        int differences = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] != y[i]) {
                differences++;
            }
        }
        return differences == 0 ? Result.EQUALS : differences == 1 ? Result.SMUDGE : Result.DIFFERENT;
    }

    private boolean isMirror(char[][] values, int idx, boolean smudgeRequired) {
        boolean smudgeFound = !smudgeRequired;
        for (int i = idx - 1; i >= 0 && 2 * idx + 1 - i < values.length; i--) {
            Result result = compare(values[i], values[2 * idx + 1 - i]);
            if (result == Result.DIFFERENT) {
                return false;
            }
            if (result == Result.SMUDGE) {
                if (!smudgeFound) {
                    smudgeFound = true;
                } else {
                    return false;
                }
            }
        }
        return smudgeFound;
    }

    private char[][] transpose(char[][] data) {
        char[][] result = new char[data[0].length][data.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                result[j][i] = data[i][j];
            }
        }
        return result;
    }

    public enum Result {
        EQUALS, SMUDGE, DIFFERENT;
    }
}
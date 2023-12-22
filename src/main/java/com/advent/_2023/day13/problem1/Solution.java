package com.advent._2023.day13.problem1;

import com.advent._2023.Parser;

import java.util.ArrayList;
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
        int result = check(getColumnInformation(data));
        if (result == -1) {
            result = check(getRowInformation(data)) * 100;
        }
        return result;
    }

    private int check(List<Long> values) {
        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i).equals(values.get(i + 1))) {
                if (isMirror(values, i)) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    private boolean isMirror(List<Long> values, int idx) {
        for (int i = idx - 1; i >= 0 && 2 * idx + 1 - i < values.size(); i--) {
            if (!values.get(i).equals(values.get(2 * idx + 1 - i))) {
                return false;
            }
        }
        return true;
    }

    private List<Long> getRowInformation(char[][] data) {
        List<Long> results = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            char[] row = data[i];
            long value = 0;
            for (char c : row) {
                value += (c == '#' ? 1 : 0);
                value <<= 1;
            }
            results.add(value);
        }
        return results;
    }

    private List<Long> getColumnInformation(char[][] data) {
        List<Long> results = new ArrayList<>();
        for (int j = 0; j < data[0].length; j++) {
            long value = 0;
            for (char[] row : data) {
                value += (row[j] == '#' ? 1 : 0);
                value <<= 1;
            }
            results.add(value);
        }
        return results;
    }
}
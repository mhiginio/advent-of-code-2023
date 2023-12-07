package com.advent._2023.day01problem2;

import com.advent._2023.Parser;

import java.util.Map;

public class Solution {
    private final Map<String, Integer> textualDigits = Map.of("one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9);

    public int solution(Parser parser) {
        return parser.streamLines().mapToInt(this::calibrate).sum();
    }


    public int calibrate(String s) {
        return firstDigit(s) * 10 + lastDigit(s);
    }

    private int firstDigit(String s) {
        char[] chars = s.toCharArray();
        int i = 0;

        while (i < chars.length && !Character.isDigit(chars[i])) {
            i++;
        }
        int value = i < chars.length ? chars[i] - '0' : 0;

        for (Map.Entry<String, Integer> entry : textualDigits.entrySet()) {
            int idx = s.indexOf(entry.getKey());
            if (idx > -1 && idx < i) {
                i = idx;
                value = entry.getValue();
            }
        }
        return value;
    }

    private int lastDigit(String s) {
        char[] chars = s.toCharArray();
        int i = chars.length - 1;

        while (i >= 0 && !Character.isDigit(chars[i])) {
            i--;
        }
        int value = i >= 0 ? chars[i] - '0' : 0;

        for (Map.Entry<String, Integer> entry : textualDigits.entrySet()) {
            int idx = s.lastIndexOf(entry.getKey());
            if (idx > -1 && idx > i) {
                i = idx;
                value = entry.getValue();
            }
        }
        return value;
    }

}

package com.advent._2023.day01.problem1;

import com.advent._2023.Parser;

public class Solution {

    public int solution(Parser parser) {
        return parser.streamLines().mapToInt(this::calibrate).sum();
    }

    public int calibrate(String s) {
        char[] chars = s.toCharArray();
        return firstNumber(chars) * 10 + lastNumber(chars);
    }

    private int firstNumber(char[] chars) {
        int i = 0;
        while (i < chars.length && !Character.isDigit(chars[i])) {
            i++;
        }
        return chars[i] - '0';
    }

    private int lastNumber(char[] chars) {
        int i = chars.length - 1;
        while (i >= 0 && !Character.isDigit(chars[i])) {
            i--;
        }
        return chars[i] - '0';
    }
}

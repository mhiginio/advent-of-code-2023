package com.advent._2023.day15problem1;

import com.advent._2023.Parser;

import java.util.Arrays;

public class Solution {
    public long solution(Parser parser) {
        String[] words = parser.streamLines().map(k -> k.split(",")).findFirst().orElseThrow();
        return Arrays.stream(words).mapToInt(this::hash).sum();
    }

    public int hash(String s) {
        int result = 0;
        for (char c : s.toCharArray()) {
            result += ascii(c);
            result *= 17;
            result %= 256;
        }
        return result;
    }

    private int ascii(char c) {
        return c - '0' + 48;
    }

}
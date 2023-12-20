package com.advent._2023.day20problem2;

import com.advent._2023.InputParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void provided_data_should_return_224602953547789() {
        assertEquals(224602953547789L,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day20problem1/input.txt"))));
    }
}
package com.advent._2023.day09problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void first_example_data_should_return_114() {
        String[] data = {
                "0 3 6 9 12 15",
                "1 3 6 10 15 21",
                "10 13 16 21 30 45"
        };
        assertEquals(114, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_1938731307() {
        assertEquals(1938731307,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day09problem1/input.txt"))));
    }
}
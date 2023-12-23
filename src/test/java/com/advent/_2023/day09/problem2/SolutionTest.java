package com.advent._2023.day09.problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import com.advent._2023.day09.problem2.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void first_example_data_should_return_2() {
        String[] data = {
                "0 3 6 9 12 15",
                "1 3 6 10 15 21",
                "10 13 16 21 30 45"
        };
        assertEquals(2, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_948() {
        assertEquals(948,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day09/input.txt"))));
    }
}
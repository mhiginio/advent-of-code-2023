package com.advent._2023.day21.problem2;

import com.advent._2023.InputParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {

    @Test
    void provided_data_should_return_590104708070703() {
        Solution solution = new Solution();
        InputParser parser = new InputParser(getClass().getResourceAsStream("/day21/input.txt"));
        assertEquals(590104708070703L, solution.solution(parser, 26501365));
    }

    @Test
    void provided_data_for_458_steps_should_return_() {
        // 458 = 3*rows + remainder, just to check the interpolation
        Solution solution = new Solution();
        InputParser parser = new InputParser(getClass().getResourceAsStream("/day21/input.txt"));
        assertEquals(177244, solution.solution(parser, 458));
    }
}
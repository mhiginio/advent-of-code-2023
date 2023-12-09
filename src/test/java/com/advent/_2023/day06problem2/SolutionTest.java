package com.advent._2023.day06problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void example_data_should_return_71503() {
        String[] data = {
                "Time:      7  15   30",
                "Distance:  9  40  200"};
        assertEquals(71503, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_34454850() {
        assertEquals(34454850,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day06problem2/input.txt"))));
    }
}
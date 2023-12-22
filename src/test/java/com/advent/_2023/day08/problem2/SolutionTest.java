package com.advent._2023.day08.problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import com.advent._2023.day08.problem2.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void first_example_data_should_return_6() {
        String[] data = {
                "LR",
                "",
                "11A = (11B, XXX)",
                "11B = (XXX, 11Z)",
                "11Z = (11B, XXX)",
                "22A = (22B, XXX)",
                "22B = (22C, 22C)",
                "22C = (22Z, 22Z)",
                "22Z = (22B, 22B)",
                "XXX = (XXX, XXX)"
        };
        assertEquals(6, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_13830919117339() {
        assertEquals(13830919117339L,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day08problem2/input.txt"))));
    }
}
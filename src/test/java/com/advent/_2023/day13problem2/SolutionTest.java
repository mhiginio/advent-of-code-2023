package com.advent._2023.day13problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void example_data_should_return_400() {

        String[] data = {
                "#.##..##.",
                "..#.##.#.",
                "##......#",
                "##......#",
                "..#.##.#.",
                "..##..##.",
                "#.#.##.#.",
                "",
                "#...##..#",
                "#....#..#",
                "..##..###",
                "#####.##.",
                "#####.##.",
                "..##..###",
                "#....#..#"
        };
        assertEquals(400, solution.solution(new StringParser(data)));
    }


    @Test
    void provided_data_should_return_31539() {
        assertEquals(31539,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day13problem1/input.txt"))));
    }
}
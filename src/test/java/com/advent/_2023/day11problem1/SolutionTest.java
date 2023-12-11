package com.advent._2023.day11problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void example_data_should_return_374() {
        String[] data = {
                "...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#....."
        };
        assertEquals(374, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_9724940() {
        assertEquals(9724940,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day11problem1/input.txt"))));
    }
}
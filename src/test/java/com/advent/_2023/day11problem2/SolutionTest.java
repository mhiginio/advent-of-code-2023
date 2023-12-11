package com.advent._2023.day11problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void example_data_should_return_82000210() {
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
        assertEquals(82000210, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_569052586852() {
        assertEquals(569052586852L,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day11problem2/input.txt"))));
    }
}
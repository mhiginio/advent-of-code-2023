package com.advent._2023.day10.problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void first_example_data_should_return_4() {
        String[] data = {
                "-L|F7",
                "7S-7|",
                "L|7||",
                "-L-J|",
                "L|-JF"
        };
        assertEquals(4, solution.solution(new StringParser(data)));
    }

    @Test
    void second_example_data_should_return_8() {
        String[] data = {
                "7-F7-",
                ".FJ|7",
                "SJLL7",
                "|F--J",
                "LJ.LJ"
        };
        assertEquals(8, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_6800() {
        assertEquals(6800,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day10/input.txt"))));
    }
}
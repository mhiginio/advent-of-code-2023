package com.advent._2023.day20.problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void first_example_data_should_return_32000000() {

        String[] data = {
                "broadcaster -> a, b, c",
                "%a -> b",
                "%b -> c",
                "%c -> inv",
                "&inv -> a"
        };
        assertEquals(32000000L, solution.solution(new StringParser(data)));
    }

    @Test
    void second_example_data_should_return_11687500() {

        String[] data = {
                "broadcaster -> a",
                "%a -> inv, con",
                "&inv -> b",
                "%b -> con",
                "&con -> output"
        };
        assertEquals(11687500L, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_808146535() {
        assertEquals(808146535L,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day20/input.txt"))));
    }
}
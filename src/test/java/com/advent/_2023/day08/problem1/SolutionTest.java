package com.advent._2023.day08.problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void first_example_data_should_return_2() {
        String[] data = {
                "RL",
                "",
                "AAA = (BBB, CCC)",
                "BBB = (DDD, EEE)",
                "CCC = (ZZZ, GGG)",
                "DDD = (DDD, DDD)",
                "EEE = (EEE, EEE)",
                "GGG = (GGG, GGG)",
                "ZZZ = (ZZZ, ZZZ)"};
        assertEquals(2, solution.solution(new StringParser(data)));
    }

    @Test
    void second_example_data_should_return_6() {
        String[] data = {
                "LLR",
                "",
                "AAA = (BBB, BBB)",
                "BBB = (AAA, ZZZ)",
                "ZZZ = (ZZZ, ZZZ)"};
        assertEquals(6, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_15989() {
        assertEquals(15989,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day08/input.txt"))));
    }
}
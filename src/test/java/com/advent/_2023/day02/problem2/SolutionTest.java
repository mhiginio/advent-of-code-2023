package com.advent._2023.day02.problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import com.advent._2023.day02.problem2.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();


    @Test
    public void example_data_should_return_2286() {
        String[] data = {
                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"};
        assertEquals(2286, solution.solution(new StringParser(data)));
    }

    @Test
    public void provided_data_should_return_49710() {
        assertEquals(49710,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day02/input.txt"))));
    }


}
package com.advent._2023.day22.problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void after_6_steps_the_elf_can_reach_16_garden_plots() {
        String[] data = {
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        };
        assertEquals(16, solution.solution(new StringParser(data), 6));
    }

    @Test
    void provided_data_should_return_3532() {
        Solution solution = new Solution();
        InputParser parser = new InputParser(getClass().getResourceAsStream("/day22problem1/input.txt"));
        assertEquals(3532, solution.solution(parser));
    }
}
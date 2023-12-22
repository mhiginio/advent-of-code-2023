package com.advent._2023.day16.problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void example_data_should_return_46() {

        String[] data = {
                ".|...\\....",
                "|.-.\\.....",
                ".....|-...",
                "........|.",
                "..........",
                ".........\\",
                "..../.\\\\..",
                ".-.-/..|..",
                ".|....-|.\\",
                "..//.|...."
        };
        assertEquals(46, solution.solution(new StringParser(data)));
    }


    @Test
    void provided_data_should_return_7517() {
        assertEquals(7517,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day16/input.txt"))));
    }
}
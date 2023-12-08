package com.advent._2023.day04problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void should_compute_correct_solution_for_example_data() {
        String[] schematicData = {
                "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
        };
        int result = solution.solution(new StringParser(schematicData));
        assertEquals(30, result);
    }

    @Test
    public void provided_data_should_return_8805731() {
        assertEquals(8805731,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day04problem2/input.txt"))));
    }
}
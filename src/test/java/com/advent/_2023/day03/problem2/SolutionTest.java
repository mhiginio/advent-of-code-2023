package com.advent._2023.day03.problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import com.advent._2023.day03.problem2.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void should_compute_correct_solution() {
        String[] schematicData = {
                "...123.3456.*3",
                "1xx*4K#32...23"
        };
        int result = solution.solution(new StringParser(schematicData));
        assertEquals(123 * 4 + 3 * 23, result);
    }

    @Test
    void should_compute_correct_solution_for_example_data() {
        String[] schematicData = {
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598.."
        };
        int result = solution.solution(new StringParser(schematicData));
        assertEquals(467835, result);
    }

    @Test
    public void provided_data_should_return_79026871() {
        assertEquals(79026871,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day03problem2/input.txt"))));
    }
}
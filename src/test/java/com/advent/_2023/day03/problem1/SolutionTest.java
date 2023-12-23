package com.advent._2023.day03.problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void should_get_correct_numbers_from_schematic() {
        String[] schematicData = {
                "...123.3456..3",
                "1xxx4K#32...23"
        };
        Solution.Schematic schematic =
                new Solution.Schematic(Arrays.stream(schematicData).map(String::toCharArray).toList());
        List<Solution.SchematicNumber> schematicNumbers = schematic.getSchematicNumbers();
        assertEquals(7, schematicNumbers.size());
        assertEquals(
                List.of(123, 3456, 3, 1, 4, 32, 23),
                schematicNumbers.stream().map(Solution.SchematicNumber::getNumber).toList());
        assertEquals(
                List.of(3, 4, 1, 1, 1, 2, 2),
                schematicNumbers.stream().map(Solution.SchematicNumber::getLength).toList());
        assertEquals(
                List.of(3, 7, 13, 0, 4, 7, 12),
                schematicNumbers.stream().map(Solution.SchematicNumber::getX).toList());
        assertEquals(
                List.of(0, 0, 0, 1, 1, 1, 1),
                schematicNumbers.stream().map(Solution.SchematicNumber::getY).toList());
    }

    @Test
    void should_compute_correct_solution() {
        String[] schematicData = {
                "...123.3456..3",
                "1xxx4K#32...23"
        };
        int result = solution.solution(new StringParser(schematicData));
        assertEquals(123 + 1 + 4 + 32 + 3456, result);
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
        assertEquals(4361, result);
    }

    @Test
    public void provided_data_should_return_527364() {
        assertEquals(527364,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day03/input.txt"))));
    }
}
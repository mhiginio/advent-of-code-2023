package com.advent._2023.day17.problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import com.advent._2023.day17.problem1.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution(4, 10);

    @Test
    void example_data_should_return_94() {

        String[] data = {
                "2413432311323",
                "3215453535623",
                "3255245654254",
                "3446585845452",
                "4546657867536",
                "1438598798454",
                "4457876987766",
                "3637877979653",
                "4654967986887",
                "4564679986453",
                "1224686865563",
                "2546548887735",
                "4322674655533"
        };
        assertEquals(94, solution.solution(new StringParser(data)));
    }


    @Test
    void provided_data_should_return_825() {
        assertEquals(825,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day17/input.txt"))));
    }
}
package com.advent._2023.day14problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void example_data_should_return_136() {

        String[] data = {
                "O....#....",
                "O.OO#....#",
                ".....##...",
                "OO.#O....O",
                ".O.....O#.",
                "O.#..O.#.#",
                "..O..#O..O",
                ".......O..",
                "#....###..",
                "#OO..#...."
        };
        assertEquals(136, solution.solution(new StringParser(data)));
    }


    @Test
    void provided_data_should_return_107053() {
        assertEquals(107053,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day14problem1/input.txt"))));
    }
}
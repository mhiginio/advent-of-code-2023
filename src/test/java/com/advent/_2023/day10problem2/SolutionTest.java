package com.advent._2023.day10problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void first_example_data_should_return_4() {
        String[] data = {
                "...........",
                ".S-------7.",
                ".|F-----7|.",
                ".||.....||.",
                ".||.....||.",
                ".|L-7.F-J|.",
                ".|..|.|..|.",
                ".L--J.L--J.",
                "..........."
        };
        assertEquals(4, solution.solution(new StringParser(data)));
    }

    @Test
    void second_example_data_should_return_8() {
        String[] data = {
                ".F----7F7F7F7F-7....",
                ".|F--7||||||||FJ....",
                ".||.FJ||||||||L7....",
                "FJL7L7LJLJ||LJ.L-7..",
                "L--J.L7...LJS7F-7L7.",
                "....F-J..F7FJ|L7L7L7",
                "....L7.F7||L7|.L7L7|",
                ".....|FJLJ|FJ|F7|.LJ",
                "....FJL-7.||.||||...",
                "....L---J.LJ.LJLJ..."
        };
        assertEquals(8, solution.solution(new StringParser(data)));
    }

    @Test
    void third_example_data_should_return_10() {
        String[] data = {
                "FF7FSF7F7F7F7F7F---7",
                "L|LJ||||||||||||F--J",
                "FL-7LJLJ||||||LJL-77",
                "F--JF--7||LJLJIF7FJ-",
                "L---JF-JLJIIIIFJLJJ7",
                "|F|F-JF---7IIIL7L|7|",
                "|FFJF7L7F-JF7IIL---7",
                "7-L-JL7||F7|L7F-7F7|",
                "L.L7LFJ|||||FJL7||LJ",
                "L7JLJL-JLJLJL--JLJ.L"
        };
        assertEquals(10, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_494() {
        assertEquals(494,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day10problem2/input.txt"))));
    }
}
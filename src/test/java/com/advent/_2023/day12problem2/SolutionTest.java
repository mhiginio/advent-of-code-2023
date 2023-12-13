package com.advent._2023.day12problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void example_data_should_return_525152() {

        String[] data = {
                "???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1"
        };
        assertEquals(525152, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_1672318386674() {
        assertEquals(1672318386674L,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day12problem2/input.txt"))));
    }
}
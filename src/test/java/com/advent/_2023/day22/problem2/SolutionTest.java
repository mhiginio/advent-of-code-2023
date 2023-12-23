package com.advent._2023.day22.problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void example_data_should_return_7() {
        String[] data = {
                "1,0,1~1,2,1",
                "0,0,2~2,0,2",
                "0,2,3~2,2,3",
                "0,0,4~0,2,4",
                "2,0,5~2,2,5",
                "0,1,6~2,1,6",
                "1,1,8~1,1,9"
        };
        assertEquals(7, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_57770() {
        Solution solution = new Solution();
        InputParser parser = new InputParser(getClass().getResourceAsStream("/day22/input.txt"));
        assertEquals(57770, solution.solution(parser));
    }
}
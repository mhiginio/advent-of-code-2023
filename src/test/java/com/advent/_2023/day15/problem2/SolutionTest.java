package com.advent._2023.day15.problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    void example_data_should_return_145() {

        String[] data = {"rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"};
        assertEquals(145, solution.solution(new StringParser(data)));
    }


    @Test
    void provided_data_should_return_294474() {
        assertEquals(294474,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day15/input.txt"))));
    }
}
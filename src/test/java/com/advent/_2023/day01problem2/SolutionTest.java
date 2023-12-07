package com.advent._2023.day01problem2;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @ParameterizedTest
    @MethodSource("com.advent._2023.day01problem2.SolutionTest#calibrationParams")
    void should_compute_correct_calibration(String line, int expected) {
        assertEquals(expected, solution.calibrate(line));
    }

    @Test
    public void should_compute_correct_value_for_a_set_of_calibrations() {
        String[] data = new String[]{
                "two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234"
        };
        assertEquals(205, solution.solution(new StringParser(data)));
    }

    @Test
    public void should_compute_correct_value_for_provided_data() {
        assertEquals(54265,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day01problem1/input.txt"))));
    }

    public static Stream<Arguments> calibrationParams() {
        return Stream.of(
                Arguments.arguments("two1nine", 29),
                Arguments.arguments("eightwothree", 83),
                Arguments.arguments("abcone2threexyz", 13),
                Arguments.arguments("xtwone3four", 24),
                Arguments.arguments("4nineeightseven2", 42),
                Arguments.arguments("zoneight234", 14),
                Arguments.arguments("7pqrstsixteen", 76)
        );
    }


}
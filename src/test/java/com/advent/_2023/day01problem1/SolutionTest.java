package com.advent._2023.day01problem1;

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
    @MethodSource("com.advent._2023.day01problem1.SolutionTest#calibrationParams")
    void should_compute_correct_calibration(String line, int expected) {
        assertEquals(expected, solution.calibrate(line));
    }

    @Test
    public void should_compute_correct_value_for_a_set_of_calibrations() {
        String[] data = new String[]{
                "1a3",
                "4321a331",
                "a4321a331d",
                "aa7hh",
                "1fff",
                "9"
        };
        assertEquals(282, solution.solution(new StringParser(data)));
    }

    @Test
    public void should_compute_correct_value_for_provided_data() {
        assertEquals(54450, solution.solution(new InputParser(getClass().getResourceAsStream("/day01problem1/input.txt"))));
    }

    public static Stream<Arguments> calibrationParams() {
        return Stream.of(
                Arguments.arguments("1a3", 13),
                Arguments.arguments("4321a331", 41),
                Arguments.arguments("a4321a331d", 41),
                Arguments.arguments("aa7hh", 77),
                Arguments.arguments("1fff", 11),
                Arguments.arguments("9", 99)
        );
    }


}
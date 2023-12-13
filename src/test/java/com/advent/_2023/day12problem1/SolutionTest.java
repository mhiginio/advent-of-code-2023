package com.advent._2023.day12problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @ParameterizedTest
    @MethodSource("com.advent._2023.day12problem1.SolutionTest#conditionReportData")
    void condition_reports_parse_should_be_correct(String rawData, String expectedSpringData,
                                                   List<Integer> expectedSpringSize) {
        Solution.ConditionReport conditionReport = Solution.ConditionReport.parse(rawData);
        assertEquals(expectedSpringData, new String(conditionReport.getSpringData()));
        assertEquals(expectedSpringSize, conditionReport.getDamagedSpringSizes());
    }

    public static Stream<Arguments> conditionReportData() {
        return Stream.of(
                Arguments.of("???.### 1,1,3", "???.###", List.of(1, 1, 3)),
                Arguments.of(".??..??...?##. 1,1,3", ".??..??...?##.", List.of(1, 1, 3)),
                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", "?#?#?#?#?#?#?#?", List.of(1, 3, 1, 6)),
                Arguments.of("????.#...#... 4,1,1", "????.#...#...", List.of(4, 1, 1)),
                Arguments.of("????.######..#####. 1,6,5", "????.######..#####.", List.of(1, 6, 5)),
                Arguments.of("?###???????? 3,2,1", "?###????????", List.of(3, 2, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("com.advent._2023.day12problem1.SolutionTest#computeCombinationsData")
    void compute_combinations_should_return_correct_value(String rawData, Long expectedValue) {
        Solution.ConditionReport conditionReport = Solution.ConditionReport.parse(rawData);
        assertEquals(expectedValue, solution.solution(conditionReport));
    }

    public static Stream<Arguments> computeCombinationsData() {
        return Stream.of(
                Arguments.of("???.### 1,1,3", 1L),
                Arguments.of(".??..??...?##. 1,1,3", 4L),
                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", 1L),
                Arguments.of("????.#...#... 4,1,1", 1L),
                Arguments.of("????.######..#####. 1,6,5", 4L),
                Arguments.of("?###???????? 3,2,1", 10L)
        );
    }

    @ParameterizedTest
    @MethodSource("com.advent._2023.day12problem1.SolutionTest#getQuestionMarksData")
    void get_question_marks_should_return_correct_values(String rawData, List<Integer> expectedValue) {
        Solution.ConditionReport conditionReport = Solution.ConditionReport.parse(rawData);
        assertEquals(expectedValue, conditionReport.getQuestionMarks());
    }

    public static Stream<Arguments> getQuestionMarksData() {
        return Stream.of(
                Arguments.of("???.### 1,1,3", List.of(0, 1, 2)),
                Arguments.of(".??..??...?##. 1,1,3", List.of(1, 2, 5, 6, 10)),
                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", List.of(0, 2, 4, 6, 8, 10, 12, 14)),
                Arguments.of("????.#...#... 4,1,1", List.of(0, 1, 2, 3)),
                Arguments.of("????.######..#####. 1,6,5", List.of(0, 1, 2, 3)),
                Arguments.of("?###???????? 3,2,1", List.of(0, 4, 5, 6, 7, 8, 9, 10, 11))
        );
    }

    @Test
    void example_data_should_return_21() {

        String[] data = {
                "???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1"
        };
        assertEquals(21, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_7204() {
        assertEquals(7204L,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day12problem1/input.txt"))));
    }
}
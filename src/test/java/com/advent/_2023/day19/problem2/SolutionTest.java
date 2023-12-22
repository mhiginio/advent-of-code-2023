package com.advent._2023.day19.problem2;

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
    @MethodSource("com.advent._2023.day19.problem2.SolutionTest#rangeSizeData")
    void range_should_return_correct_size(Solution.Range range, int expectedSize) {
        assertEquals(expectedSize, range.size());
    }

    private static Stream<Arguments> rangeSizeData() {
        return Stream.of(
                Arguments.of(new Solution.Range(0, 10), 11),
                Arguments.of(new Solution.Range(2, 5), 4),
                Arguments.of(new Solution.Range(4, 5), 2),
                Arguments.of(new Solution.Range(2, 11), 10),
                Arguments.of(new Solution.Range(3, 1), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("com.advent._2023.day19.problem2.SolutionTest#testData")
    void solution_should_be_correct(String[] data, long expectedSolution) {
        assertEquals(expectedSolution, solution.solution(new StringParser(data)));
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(new String[]{
                        "",
                        "in{x<1000:A,x<500:R,R}"
                }, 4000L * 4000L * 4000L * 999L),
                Arguments.of(new String[]{
                        "",
                        "in{x<1000:A,a<500:A,R}"
                }, 4000L * 4000L * 4000L * 999L + 4000L * 4000L * 499L * 3001L),
                Arguments.of(new String[]{
                        "",
                        "in{x<1000:R,A}"
                }, 4000L * 4000L * 4000L * 3001L),
                Arguments.of(new String[]{
                        "",
                        "in{x<1000:R,m>1000:R,A}"
                }, 4000L * 4000L * 1000L * 3001L),
                Arguments.of(new String[]{
                        "",
                        "in{x<1000:kk,m>1000:R,A}",
                        "kk{a>2000:R,A}"
                }, 4000L * 4000L * 999L * 2000L + 4000L * 4000L * 1000L * 3001L),
                Arguments.of(new String[]{
                        "",
                        "in{x<1000:R,x>999:R,A}"
                }, 0L),
                Arguments.of(new String[]{
                        "",
                        "in{x<1000:kk,x>999:R,A}",
                        "kk{a>2000:R,a<2001:R,A}"
                }, 0L)
        );
    }

    @Test
    void example_data_should_return_167409079868000() {

        String[] data = {
                "px{a<2006:qkq,m>2090:A,rfg}",
                "pv{a>1716:R,A}",
                "lnx{m>1548:A,A}",
                "rfg{s<537:gd,x>2440:R,A}",
                "qs{s>3448:A,lnx}",
                "qkq{x<1416:A,crn}",
                "crn{x>2662:A,R}",
                "in{s<1351:px,qqz}",
                "qqz{s>2770:qs,m<1801:hdj,R}",
                "gd{a>3333:R,R}",
                "hdj{m>838:A,pv}",
                "",
                "{x=787,m=2655,a=1222,s=2876}",
                "{x=1679,m=44,a=2067,s=496}",
                "{x=2036,m=264,a=79,s=2244}",
                "{x=2461,m=1339,a=466,s=291}",
                "{x=2127,m=1623,a=2188,s=1013}"
        };
        assertEquals(167409079868000L, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_116738260946855() {
        assertEquals(116738260946855L,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day19/input.txt"))));
    }
}
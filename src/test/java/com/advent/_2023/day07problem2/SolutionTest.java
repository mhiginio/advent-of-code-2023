package com.advent._2023.day07problem2;

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
    @MethodSource("com.advent._2023.day07problem1.SolutionTest#handTypeData")
    void type_should_be_recognized_properly(String rawHand, int type) {
        com.advent._2023.day07problem1.Solution.Hand hand = com.advent._2023.day07problem1.Solution.Hand.parse(rawHand);
        assertEquals(type, hand.getType());
    }

    public static Stream<Arguments> handTypeData() {
        return Stream.of(
                Arguments.of("1J2TK 123", com.advent._2023.day07problem1.Solution.Hand.ONE_PAIR),
                Arguments.of("T55J5 4", com.advent._2023.day07problem1.Solution.Hand.FOUR_OF_A_KIND),
                Arguments.of("KTJJT 87", com.advent._2023.day07problem1.Solution.Hand.FOUR_OF_A_KIND),
                Arguments.of("AJAAA 87", com.advent._2023.day07problem1.Solution.Hand.FIVE_OF_A_KIND),
                Arguments.of("J3A33 87", com.advent._2023.day07problem1.Solution.Hand.FOUR_OF_A_KIND),
                Arguments.of("A3AJ3 87", com.advent._2023.day07problem1.Solution.Hand.FULL_HOUSE),
                Arguments.of("123J5 87", com.advent._2023.day07problem1.Solution.Hand.ONE_PAIR)
        );
    }
    @Test
    void example_data_should_return_5905() {
        String[] data = {
                "32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483"};
        assertEquals(5905, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_243101568() {
        assertEquals(243101568,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day07problem2/input.txt"))));
    }
}
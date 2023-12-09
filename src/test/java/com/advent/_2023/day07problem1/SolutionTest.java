package com.advent._2023.day07problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    private final Solution solution = new Solution();

    @ParameterizedTest
    @MethodSource("com.advent._2023.day07problem1.SolutionTest#handData")
    void hand_parse_should_be_correct(String rawHand, char[] expectedHand, int expectedBid) {
        Solution.Hand hand = Solution.Hand.parse(rawHand);
        assertArrayEquals(expectedHand, hand.getCards());
        assertEquals(expectedBid, hand.getBid());
    }

    public static Stream<Arguments> handData() {
        return Stream.of(
                Arguments.of("112TK 123", "112TK".toCharArray(), 123),
                Arguments.of("T55J5 4", "T55J5".toCharArray(), 4),
                Arguments.of("KTJJT 87", "KTJJT".toCharArray(), 87)
        );
    }

    @ParameterizedTest
    @MethodSource("com.advent._2023.day07problem1.SolutionTest#handTypeData")
    void type_should_be_recognized_properly(String rawHand, int type) {
        Solution.Hand hand = Solution.Hand.parse(rawHand);
        assertEquals(type, hand.getType());
    }

    public static Stream<Arguments> handTypeData() {
        return Stream.of(
                Arguments.of("112TK 123", Solution.Hand.ONE_PAIR),
                Arguments.of("T55J5 4", Solution.Hand.THREE_OF_A_KIND),
                Arguments.of("KTJJT 87", Solution.Hand.TWO_PAIR),
                Arguments.of("AAAAA 87", Solution.Hand.FIVE_OF_A_KIND),
                Arguments.of("33A33 87", Solution.Hand.FOUR_OF_A_KIND),
                Arguments.of("A3A33 87", Solution.Hand.FULL_HOUSE),
                Arguments.of("12345 87", Solution.Hand.HIGH_CARD)
        );
    }

    @Test
    void example_data_should_return_6440() {
        String[] data = {
                "32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483"};
        assertEquals(6440, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_241344943() {
        assertEquals(241344943,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day07problem1/input.txt"))));
    }
}
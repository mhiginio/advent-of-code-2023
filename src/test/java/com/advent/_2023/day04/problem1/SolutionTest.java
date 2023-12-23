package com.advent._2023.day04.problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    private final Solution solution = new Solution();

    @ParameterizedTest
    @MethodSource("com.advent._2023.day04.problem1.SolutionTest#gameCardsData")
    void parsed_game_should_contain_correct_data(String rawGame, int gameId, int[] winners, int[] played, int value) {
        Solution.Game game = Solution.Game.parse(rawGame);
        assertEquals(gameId, game.getId());
        assertEquals(stream(winners).boxed().toList(), game.getWinners());
        assertEquals(stream(played).boxed().toList(), game.getPlayed());
        assertEquals(value, game.getValue());
    }

    @Test
    void example_data_should_return_13() {
        String[] gameData = {
                "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"};
        assertEquals(13, solution.solution(new StringParser(gameData)));
    }

    @Test
    public void provided_data_should_return_25571() {
        assertEquals(25571,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day04/input.txt"))));
    }
    public static Stream<Arguments> gameCardsData() {
        return Stream.of(
                Arguments.of("Card  25:  2 90 83 |  9 90 83 23", 25, new int[]{2, 90, 83}, new int[]{9, 90, 83, 23}, 2),
                Arguments.of("Card   2: 12  1  8 |  9 11 12", 2, new int[]{12, 1, 8}, new int[]{9, 11, 12}, 1),
                Arguments.of("Card   2: 12  1  8 |  88 11 13", 2, new int[]{12, 1, 8}, new int[]{88, 11, 13}, 0)
        );
    }
}
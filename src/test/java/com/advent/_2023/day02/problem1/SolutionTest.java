package com.advent._2023.day02.problem1;

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
    @MethodSource("com.advent._2023.day02.problem1.SolutionTest#testParseData")
    void parse_should_return_correct_value(String text, Solution.Game expected) {
        assertEquals(expected, Solution.Game.parse(text));
    }

    @Test
    public void example_data_should_return_8() {
        String[] data = {
                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"};
        assertEquals(8, solution.solution(new StringParser(data)));
    }

    @Test
    public void provided_data_should_return_2683() {
        assertEquals(2683, solution.solution(new InputParser(getClass().getResourceAsStream("/day02/input.txt"))));
    }

    public static Stream<Arguments> testParseData() {
        return Stream.of(
                Arguments.arguments(
                        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                        buildGame(3,
                                new Solution.CubesData(20, 8, 6),
                                new Solution.CubesData(4, 13, 5),
                                new Solution.CubesData(1, 5, 0))),
                Arguments.arguments(
                        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                        buildGame(4,
                                new Solution.CubesData(3, 1, 6),
                                new Solution.CubesData(6, 3, 0),
                                new Solution.CubesData(14, 3, 15)))
        );
    }

    private static Object buildGame(int id, Solution.CubesData... cubesData) {
        Solution.Game game = new Solution.Game();
        game.setId(id);
        game.setCubesDataCollection(List.of(cubesData));
        return game;
    }
}
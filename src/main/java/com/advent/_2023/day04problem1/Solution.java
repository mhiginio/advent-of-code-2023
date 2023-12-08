package com.advent._2023.day04problem1;

import com.advent._2023.Parser;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public int solution(Parser parser) {
        return parser.streamLines().map(Game::parse).mapToInt(Game::getValue).sum();
    }

    @Data
    public static class Game {
        private int id;
        private List<Integer> winners;
        private List<Integer> played;

        public int getValue() {
            HashSet<Integer> played = new HashSet<>(this.played);
            played.retainAll(winners);
            if (played.isEmpty()) {
                return 0;
            }
            return (int) Math.pow(2, played.size() - 1);
        }

        public static Game parse(String rawGame) {
            Game result = new Game();
            String[] split = rawGame.split(":");
            result.parseId(split[0]);
            result.parseGameData(split[1].split("\\|"));
            return result;
        }

        private void parseGameData(String[] gameData) {
            winners = parseIntegers(gameData[0]);
            played = parseIntegers(gameData[1]);
        }

        private List<Integer> parseIntegers(String gameData) {
            Scanner scanner = new Scanner(gameData);
            List<Integer> result = new ArrayList<>();
            while (scanner.hasNext()) {
                result.add(Integer.parseInt(scanner.next()));
            }
            return result;
        }

        private void parseId(String data) {
            id = Integer.parseInt(data.substring(data.lastIndexOf(' ') + 1));
        }
    }
}
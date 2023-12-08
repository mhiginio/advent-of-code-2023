package com.advent._2023.day04problem2;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public int solution(Parser parser) {
        GameCollection games = new GameCollection(parser.streamLines().map(Game::parse).toList());
        return games.process();
    }

    @AllArgsConstructor
    public static class GameCollection {
        private List<Game> games;

        public int process() {
            for (int i = 0; i < games.size(); i++) {
                Game game = games.get(i);
                int value = game.getValue();
                for (int j = 0; j < value && i + 1 + j < games.size(); j++) {
                    games.get(i + 1 + j).addWeight(game.getWeight());
                }
            }
            return games.stream().mapToInt(Game::getWeight).sum();
        }
    }

    @Data
    public static class Game {
        private int id;
        private int weight = 1;
        private List<Integer> winners;
        private List<Integer> played;

        public int getValue() {
            HashSet<Integer> played = new HashSet<>(this.played);
            played.retainAll(winners);
            return played.size();
        }

        public void addWeight(int value) {
            weight += value;
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
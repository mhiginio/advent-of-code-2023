package com.advent._2023.day02problem2;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Arrays.stream;

public class Solution {
    public int solution(Parser parser) {
        return parser.streamLines().map(Game::parse).map(this::maximumCubeData).mapToInt(this::power).sum();
    }

    private int power(CubesData cubesData) {
        return cubesData.getRed() * cubesData.getGreen() * cubesData.getBlue();
    }

    private CubesData maximumCubeData(Game game) {
        CubesData result = new CubesData();
        List<CubesData> cubesDataCollection = game.getCubesDataCollection();
        result.setRed(cubesDataCollection.stream().mapToInt(CubesData::getRed).max().orElse(1));
        result.setGreen(cubesDataCollection.stream().mapToInt(CubesData::getGreen).max().orElse(1));
        result.setBlue(cubesDataCollection.stream().mapToInt(CubesData::getBlue).max().orElse(1));
        return result;
    }


    @Data
    public static final class Game {
        int id;
        List<CubesData> cubesDataCollection;

        public static Game parse(String gameInfo) {
            Game game = new Game();
            game.setId(extractId(gameInfo, game));
            game.setCubesDataCollection(extractCubeCollections(gameInfo));
            return game;
        }

        private static List<CubesData> extractCubeCollections(String gameInfo) {
            String[] cubes = gameInfo.substring(gameInfo.indexOf(':') + 1).split(";");
            return stream(cubes).map(CubesData::processCubes).toList();
        }

        private static int extractId(String gameInfo, Game game) {
            int idx = gameInfo.indexOf(':');
            return (Integer.parseInt(gameInfo.substring(5, idx)));
        }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CubesData {
        int red;
        int green;
        int blue;

        public static CubesData processCubes(String cubes) {
            CubesData result = new CubesData();
            String[] cubesDataInfo = cubes.split(",");
            for (String data : cubesDataInfo) {
                int value = Integer.parseInt(data.substring(1, data.lastIndexOf(' ')));
                if (data.contains("red")) {
                    result.setRed(value);
                } else if (data.contains("green")) {
                    result.setGreen(value);
                } else if (data.contains("blue")) {
                    result.setBlue(value);
                }
            }
            return result;
        }
    }
}
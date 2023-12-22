package com.advent._2023.day14.problem1;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class Solution {
    public long solution(Parser parser) {
        char[][] data = parser.streamLines().map(String::toCharArray).toArray(char[][]::new);
        Distribution distribution = Distribution.parse(data);
        distribution.slideUp();
        return distribution.computeLoad();
    }

    private static class Distribution {
        private final List<Rock> fixedRocks = new ArrayList<>();
        private final List<Rock> slidingRocks = new ArrayList<>();
        private int rows;

        public static Distribution parse(char[][] data) {
            Distribution result = new Distribution();
            result.rows = data.length;
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    if (data[i][j] == 'O') {
                        result.slidingRocks.add(new Rock(i, j));
                    } else if (data[i][j] == '#') {
                        result.fixedRocks.add(new Rock(i, j));
                    }
                }
            }
            return result;
        }


        public void slideUp() {
            // By construction upper rocks appears first in the list, so we don't have to worry about an sliding rock
            // colliding with another one that hasn't moved yet
            slidingRocks.forEach(rock -> rock.setRow(computeSlide(rock)));
        }

        private int computeSlide(Rock rock) {
            OptionalInt max = Stream.of(slidingRocks, fixedRocks).flatMap(List::stream)
                    .filter(k -> k.getColumn() == rock.getColumn())
                    .filter(k -> k.getRow() < rock.getRow())
                    .mapToInt(Rock::getRow)
                    .max();
            return max.isPresent() ? max.getAsInt() + 1 : 0;
        }

        public long computeLoad() {
            return slidingRocks.stream().mapToInt(rock -> rows - rock.getRow()).sum();
        }

    }

    @Data
    @AllArgsConstructor
    private static class Rock {
        private int row;
        private int column;
    }
}
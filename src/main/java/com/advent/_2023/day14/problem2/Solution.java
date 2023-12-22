package com.advent._2023.day14.problem2;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {

    public long solution(Parser parser) {
        int size = 400;

        char[][] data = parser.streamLines().map(String::toCharArray).toArray(char[][]::new);
        Distribution
                distribution = Distribution.parse(data);
        int total = 1000000000;
        // Warm up:
        IntStream.range(0, size).forEach(k -> distribution.cycle());
        // Collect data to detect cycles:
        List<Long> sample = IntStream.range(0, size).mapToLong(k -> {
            distribution.cycle();
            return distribution.computeLoad();
        }).boxed().toList();
        List<Integer> cycleCandidates = findCycle(sample);
        int length = findLengthOfCycle(cycleCandidates);
        // Let's find which value will become the last one considering the cycle
        // 1000000000 - length * n <size*2-100 --> 1000000000 - size*2-100 < length * n --> n > (1000000000 - size*2-100) / length
        // We know all the values from 10000th to 19999th elements
        int value = (1000000000 - (size*2-100)) / length;
        int index = 1000000000 - (length * value);
        index -= size; // The initial warm up
        return sample.get(index - 1);
    }

    private int findLengthOfCycle(List<Integer> cycleCandidates) {
        int refValue = cycleCandidates.get(0);
        for (int i = 1; i < cycleCandidates.size(); i++) {
            int cycleLength = cycleCandidates.get(i);
            if (isCycle(cycleCandidates, cycleLength, refValue)) {
                return cycleLength;
            }
        }
        return -1;
    }

    private boolean isCycle(List<Integer> cycleCandidates, int cycleLength, int refValue) {

        int testedValue = refValue;
        int max = cycleCandidates.get(cycleCandidates.size() - 1);
        while (testedValue < max && cycleCandidates.contains(testedValue)) {
            testedValue += cycleLength;
        }
        return testedValue >= max;
    }

    private List<Integer> findCycle(List<Long> sample) {
        List<Integer> result = new ArrayList<>();
        long tortoise;
        long rabbit;
        for (int i = 0; i < sample.size() / 2; i++) {
            tortoise = sample.get(i);
            rabbit = sample.get(2 * i);
            if (tortoise == rabbit) {
                result.add(i);
            }
        }
        return result;
    }

    private static class Distribution {
        private final List<Rock> fixedRocks = new ArrayList<>();
        private final List<Rock> slidingRocks = new ArrayList<>();
        private int rows;
        private int columns;

        public static Distribution parse(char[][] data) {
            Distribution
                    result = new Distribution();
            result.rows = data.length;
            result.columns = data[0].length;
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
        static int cycle=0;

        public void cycle() {
            System.out.println(cycle++);
            for (int i = 0; i < columns; i++) {
                final int column = i;
                slidingRocks.stream().filter(k -> k.getColumn() == column)
                        .sorted(Comparator.comparing(Rock::getRow))
                        .forEach(rock -> rock.setRow(slideUp(rock)));
            }
            for (int i = 0; i < rows; i++) {
                final int row = i;
                slidingRocks.stream().filter(k -> k.getRow() == row)
                        .sorted(Comparator.comparing(Rock::getColumn))
                        .forEach(rock -> rock.setColumn(slideLeft(rock)));
            }
            for (int i = 0; i < columns; i++) {
                final int column = i;
                slidingRocks.stream().filter(k -> k.getColumn() == column)
                        .sorted(Comparator.comparing(Rock::getRow).reversed())
                        .forEach(rock -> rock.setRow(slideDown(rock)));
            }
            for (int i = 0; i < rows; i++) {
                final int row = i;
                slidingRocks.stream().filter(k -> k.getRow() == row)
                        .sorted(Comparator.comparing(Rock::getColumn).reversed())
                        .forEach(rock -> rock.setColumn(slideRight(rock)));
            }
        }


        private int slideUp(Rock rock) {
            OptionalInt max = Stream.of(slidingRocks, fixedRocks).flatMap(List::stream)
                    .filter(k -> k.getColumn() == rock.getColumn())
                    .filter(k -> k.getRow() < rock.getRow())
                    .mapToInt(Rock::getRow)
                    .max();
            return max.isPresent() ? max.getAsInt() + 1 : 0;
        }

        private int slideDown(Rock rock) {
            OptionalInt min = Stream.of(slidingRocks, fixedRocks).flatMap(List::stream)
                    .filter(k -> k.getColumn() == rock.getColumn())
                    .filter(k -> k.getRow() > rock.getRow())
                    .mapToInt(Rock::getRow)
                    .min();
            return min.isPresent() ? min.getAsInt() - 1 : columns - 1;
        }

        private int slideLeft(Rock rock) {
            OptionalInt max = Stream.of(slidingRocks, fixedRocks).flatMap(List::stream)
                    .filter(k -> k.getRow() == rock.getRow())
                    .filter(k -> k.getColumn() < rock.getColumn())
                    .mapToInt(Rock::getColumn)
                    .max();
            return max.isPresent() ? max.getAsInt() + 1 : 0;
        }

        private int slideRight(Rock rock) {
            OptionalInt min = Stream.of(slidingRocks, fixedRocks).flatMap(List::stream)
                    .filter(k -> k.getRow() == rock.getRow())
                    .filter(k -> k.getColumn() > rock.getColumn())
                    .mapToInt(Rock::getColumn)
                    .min();
            return min.isPresent() ? min.getAsInt() - 1 : rows - 1;
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
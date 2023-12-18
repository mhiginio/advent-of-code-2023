package com.advent._2023.day18problem1;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Solution {

    public long solution(Parser parser) {
        Stream<String> stream = parser.streamLines();
        List<Movement> movements = stream.map(Movement::parse).toList();
        Lake lake = new Lake();
        lake.init(movements.get(0));
        movements.forEach(lake::addMovement);
        return lake.computeArea();
    }

    @Data
    private static final class Movement {
        private Direction direction;
        private int length;
        private String color;

        public static Movement parse(String raw) {
            String[] split = raw.split(" ");
            Movement result = new Movement();
            result.setDirection(Direction.of(split[0]));
            result.setLength(Integer.parseInt(split[1]));
            result.setColor(split[2]);
            return result;
        }
    }

    private static class Lake {
        private LinkedList<LinkedList<Movement>> data = new LinkedList<>();
        private int rowPosition = 0;
        private int colPosition = 0;

        public void init(Movement movement) {
            data = new LinkedList<>();
            data.add(new LinkedList<>());
            data.get(0).add(movement);
        }

        public void addMovement(Movement movement) {
            Direction direction = movement.getDirection();
            for (int i = 0; i < movement.getLength(); i++) {
                if (direction == Direction.LEFT) {
                    if (colPosition == 0) {
                        int rows = data.size();
                        for (int j = 0; j < rows; j++) {
                            data.get(j).add(0, j == rowPosition ? movement : new Movement());
                        }
                    } else {
                        colPosition--;
                        data.get(rowPosition).set(colPosition, movement);
                    }
                } else if (direction == Direction.RIGHT) {
                    if (colPosition == data.get(rowPosition).size() - 1) {
                        int rows = data.size();
                        for (int j = 0; j < rows; j++) {
                            data.get(j).add(j == rowPosition ? movement : new Movement());
                        }
                        colPosition++;
                    } else {
                        colPosition++;
                        data.get(rowPosition).set(colPosition, movement);
                    }
                } else if (direction == Direction.UP) {
                    if (rowPosition == 0) {
                        int cols = data.get(rowPosition).size();
                        data.add(0, new LinkedList<>());
                        for (int col = 0; col < cols; col++) {
                            data.get(0).add(col == colPosition ? movement : new Movement());
                        }
                    } else {
                        rowPosition--;
                        data.get(rowPosition).set(colPosition, movement);
                    }
                } else if (direction == Direction.DOWN) {
                    if (rowPosition == data.size() - 1) {
                        data.add(new LinkedList<>());
                        int cols = data.get(rowPosition).size();
                        rowPosition++;
                        for (int col = 0; col < cols; col++) {
                            data.get(rowPosition).add(col == colPosition ? movement : new Movement());
                        }
                    } else {
                        rowPosition++;
                        data.get(rowPosition).set(colPosition, movement);
                    }
                }
            }
        }

        public long computeArea() {
            boolean clockwise = computeDirection();
            long result = 0;
            for (int row = 0; row < data.size(); row++) {
                StringBuilder rowString = new StringBuilder();
                for (int col = 0; col < data.get(0).size(); col++) {
                    if (data.get(row).get(col).getDirection() != null) {
                        rowString.append(data.get(row).get(col).getDirection().getCode());
                        result++;
                    } else if (isInside(row, col, clockwise)) {
                        rowString.append("#");
                        result++;
                    } else {
                        rowString.append(" ");
                    }
                }
                System.out.println(rowString);
            }
            return result;
        }

        private boolean computeDirection() {
            Movement first = data.get(0).stream()
                    .filter(k -> k.getDirection() == Direction.LEFT || k.getDirection() == Direction.RIGHT).findFirst()
                    .orElseThrow();
            return first.getDirection() == Direction.RIGHT;
        }

        private boolean isInside(int row, int col, boolean clockwise) {
            if (row == 0 || col == 0 || row == data.size() - 1 || col == data.get(0).size() - 1) {
                return false;
            }
            Optional<Boolean> result = testRight(row, col, clockwise);
            if (result.isPresent()) {
                return result.get();
            }
            result = testDown(row, col, clockwise);
            if (result.isPresent()) {
                return result.get();
            }
            result = testUp(row, col, clockwise);
            if (result.isPresent()) {
                return result.get();
            }
            result = testLeft(row, col, clockwise);
            return result.orElseThrow();
        }

        private Optional<Boolean> testRight(int row, int col, boolean clockwise) {
            int testCol = col + 1;
            while (testCol < data.get(0).size() && data.get(row).get(testCol).getDirection() == null) {
                testCol++;
            }
            if (testCol == data.get(0).size()) {
                return Optional.of(false);
            }
            Direction direction = data.get(row).get(testCol).getDirection();
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                return Optional.empty();
            }

            return Optional.of(direction == Direction.UP && !clockwise || direction == Direction.DOWN && clockwise);
        }

        private Optional<Boolean> testLeft(int row, int col, boolean clockwise) {
            int testCol = col - 1;
            while (testCol >= 0 && data.get(row).get(testCol).getDirection() == null) {
                testCol--;
            }
            if (testCol < 0) {
                return Optional.of(false);
            }
            Direction direction = data.get(row).get(testCol).getDirection();
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                return Optional.empty();
            }
            return Optional.of(direction == Direction.DOWN && !clockwise || direction == Direction.UP && clockwise);
        }

        private Optional<Boolean> testDown(int row, int col, boolean clockwise) {
            int testRow = row + 1;
            while (testRow < data.size() && data.get(testRow).get(col).getDirection() == null) {
                testRow++;
            }
            if (testRow == data.size()) {
                return Optional.of(false);
            }
            Direction direction = data.get(testRow).get(col).getDirection();
            if (direction == Direction.UP || direction == Direction.DOWN) {
                return Optional.empty();
            }
            return Optional.of(direction == Direction.LEFT && clockwise || direction == Direction.RIGHT && !clockwise);
        }

        private Optional<Boolean> testUp(int row, int col, boolean clockwise) {
            int testRow = row - 1;
            while (testRow >= 0 && data.get(testRow).get(col).getDirection() == null) {
                testRow--;
            }
            if (testRow < 0) {
                return Optional.of(false);
            }
            Direction direction = data.get(testRow).get(col).getDirection();
            if (direction == Direction.UP || direction == Direction.DOWN) {
                return Optional.empty();
            }
            return Optional.of(direction == Direction.RIGHT && clockwise || direction == Direction.LEFT && !clockwise);
        }
    }


    @AllArgsConstructor
    @Getter
    private enum Direction {
        UP("U"), DOWN("D"), LEFT("L"), RIGHT("R");
        private final String code;

        private static final Map<String, Direction> LOOKUP = Arrays.stream(Direction.values()).collect(
                Collectors.toMap(Direction::getCode, Function.identity()));

        public static Direction of(String code) {
            return LOOKUP.get(code);
        }
    }
}
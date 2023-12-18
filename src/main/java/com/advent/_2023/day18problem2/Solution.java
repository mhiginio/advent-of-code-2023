package com.advent._2023.day18problem2;

import com.advent._2023.Parser;
import com.advent._2023.StringParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This uses the shoelace formula and Pick's theorem
 */
public class Solution {

    public long solution(Parser parser) {
        Stream<String> stream = parser.streamLines();
        List<Movement> movements = stream.map(Movement::enhancedParse).toList();
        return processMovements(movements);
    }

    public long oldSolution(Parser parser) {
        Stream<String> stream = parser.streamLines();
        List<Movement> movements = stream.map(Movement::parse).toList();
        return processMovements(movements);
    }

    private long processMovements(List<Movement> movements) {
        List<Coordinate> coordinates = convertToCoordinates(movements);
        Long area = applyShoelaceTheorem(coordinates);
        long pointsInBoundary = getPointsInBoundary(movements);
        return applyPricksTheorem(area, pointsInBoundary) + pointsInBoundary;
    }

    private Long applyShoelaceTheorem(List<Coordinate> coordinates) {
        long result = 0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            Coordinate coordinate = coordinates.get(i);
            Coordinate nextCoordinate = coordinates.get(i + 1);
            result += ((coordinate.getX() - nextCoordinate.getX()) * (coordinate.getY() + nextCoordinate.getY()));
        }
        return Math.abs(result) / 2;
    }

    private long applyPricksTheorem(Long area, long pointsInBoundary) {
        return area - pointsInBoundary / 2 + 1;
    }

    private long getPointsInBoundary(List<Movement> movements) {
        return movements.stream().mapToInt(Movement::getLength).sum();
    }

    private List<Coordinate> convertToCoordinates(List<Movement> movements) {
        List<Coordinate> result = new ArrayList<>();
        result.add(new Coordinate(0, 0));
        int currentX = 0;
        int currentY = 0;
        for (Movement movement : movements) {
            if (movement.getDirection() == Direction.UP) {
                currentY += movement.getLength();
            } else if (movement.getDirection() == Direction.DOWN) {
                currentY -= movement.getLength();
            } else if (movement.getDirection() == Direction.LEFT) {
                currentX -= movement.getLength();
            } else if (movement.getDirection() == Direction.RIGHT) {
                currentX += movement.getLength();
            }
            result.add(new Coordinate(currentX, currentY));
        }
        return result;
    }



    @Data
    private static final class Coordinate {
        private final long x;
        private final long y;
    }

    @Data
    private static final class Movement {
        private Direction direction;
        private int length;

        public static Movement enhancedParse(String raw) {
            String[] split = raw.split(" ");
            String color = split[2].substring(2, 8);
            Movement result = new Movement();
            result.setDirection(Direction.ofIntValue(color.substring(5)));
            result.setLength(Integer.parseInt(color.substring(0, 5), 16));
            return result;
        }

        public static Movement parse(String raw) {
            String[] split = raw.split(" ");
            Movement result = new Movement();
            result.setDirection(Direction.of(split[0]));
            result.setLength(Integer.parseInt(split[1]));
            return result;
        }
    }

    @AllArgsConstructor
    @Getter
    private enum Direction {
        UP("U", "3"), DOWN("D", "1"), LEFT("L", "2"), RIGHT("R", "0");
        private final String code;
        private final String intValue;

        private static final Map<String, Direction> INTLOOKUP = Arrays.stream(Direction.values()).collect(
                Collectors.toMap(Direction::getIntValue, Function.identity()));
        private static final Map<String, Direction> LOOKUP = Arrays.stream(Direction.values()).collect(
                Collectors.toMap(Direction::getCode, Function.identity()));

        public static Direction ofIntValue(String code) {
            return INTLOOKUP.get(code);
        }

        public static Direction of(String code) {
            return LOOKUP.get(code);
        }
    }
}
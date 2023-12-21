package com.advent._2023.day21problem1;

import com.advent._2023.Parser;
import com.advent._2023.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class Solution {

    private char[][] map;
    private Integer[][] reachable;
    private List<Position> currentNodes;
    private int currentStep;
    private int totalRows;
    private int totalCols;


    public long solution(Parser parser, int steps) {
        map = parser.streamLines().map(String::toCharArray).toArray(char[][]::new);
        init();
        step(steps);
        return computeSolution(steps);
    }

    public void init() {
        totalRows = map.length;
        totalCols = map[0].length;
        currentNodes = new ArrayList<>();
        Position gardenerLocation = gardenerLocation();
        currentNodes.add(gardenerLocation);
        reachable = new Integer[totalRows][totalCols];
        reachable[gardenerLocation.getRow()][gardenerLocation.getColumn()] = 0;
    }

    private Position gardenerLocation() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == 'S') {
                    return new Position(row, col);
                }
            }
        }
        throw new IllegalArgumentException("Gardener not found");
    }

    public void step(int steps) {
        int remainingSteps = steps;
        while (remainingSteps > 0) {
            step();
            remainingSteps--;
        }
    }

    private void step() {
        List<Position> newCurrentNodes = new ArrayList<>();
        currentStep++;
        for (Position currentNode : currentNodes) {
            for (Function<Position, Position> direction : Position.DIRECTIONS) {
                Position position = direction.apply(currentNode);
                if (validPosition(position) && isGarden(position) &&undiscovered(position)) {
                    reachable[position.getRow()][position.getColumn()] = currentStep;
                    newCurrentNodes.add(position);
                }
            }
        }
        currentNodes = newCurrentNodes;
    }

    private boolean isGarden(Position position) {
        return map[position.getRow()][position.getColumn()] != '#';
    }

    private boolean undiscovered(Position position) {
        return reachable[position.getRow()][position.getColumn()] == null;
    }

    private boolean validPosition(Position position) {
        return position.getRow() >= 0 && position.getRow() < totalRows &&
                position.getColumn() >= 0 && position.getColumn() < totalCols;
    }

    private long computeSolution(int steps) {
        boolean even = steps % 2 == 0;
        long solution = 0;
        for (Integer[] row : reachable) {
            for (Integer value : row) {
                if (value != null && value <= steps && value % 2 == (even ? 0 : 1)) {
                    solution++;
                }
            }
        }
        return solution;
    }
}
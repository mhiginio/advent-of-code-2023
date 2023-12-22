package com.advent._2023.day21.problem2;

import com.advent._2023.Parser;
import com.advent._2023.utils.Position;
import com.advent._2023.utils.QuadraticFunction;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;


public class Solution {

    private char[][] map;
    private Set<ExtendedPosition> currentNodes;
    private int totalRows;
    private int totalCols;


    public long solution(Parser parser, int steps) {
        map = parser.streamLines().map(String::toCharArray).toArray(char[][]::new);
        init();
        // Having that there is no rocks on the horizontal and vertical of the gardener
        // and that the region is a square, let's assume the values follow a quadratic function each totalRows (=totalCols) steps
        // Let's get the first three values to calculate the interpolation
        int remainder = steps % totalRows;
        step(remainder);
        long count1 = computeSolution();
        step(totalRows);
        long count2 = computeSolution();
        step(totalRows);
        long count3 = computeSolution();
        double[] quadraticFunction = QuadraticFunction.findQuadraticFunction(remainder, count1,
                remainder + totalRows * 2, count3,
                remainder + totalRows, count2);
        int x = steps;
        return Math.round(quadraticFunction[0]*x*x + quadraticFunction[1]*x+quadraticFunction[2]);
    }

    public void init() {
        totalRows = map.length;
        totalCols = map[0].length;
        currentNodes = new HashSet<>();
        Position gardenerLocation = gardenerLocation();
        currentNodes.add(new ExtendedPosition(gardenerLocation, 0, 0));
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
        Set<ExtendedPosition> newCurrentNodes = new HashSet<>();
        for (ExtendedPosition currentExtendedPosition : currentNodes) {
            Position currentPosition = currentExtendedPosition.getPosition();
            for (Function<Position, Position> direction : Position.DIRECTIONS) {
                Position position = direction.apply(currentPosition);
                ExtendedPosition newExtendedPosition = getExtendedPosition(position, currentExtendedPosition);
                if (isGarden(newExtendedPosition.getPosition())) {
                    newCurrentNodes.add(newExtendedPosition);
                }
            }
        }
        currentNodes = newCurrentNodes;
    }

    private ExtendedPosition getExtendedPosition(Position pos, ExtendedPosition cep) {
        if (validPosition(pos)) {
            return new ExtendedPosition(pos, cep.getRowMap(), cep.getColumnMap());
        } else if (pos.getRow() < 0) {
            return new ExtendedPosition(new Position(totalRows - 1, pos.getColumn()), cep.getRowMap() - 1,
                    cep.getColumnMap());
        } else if (pos.getRow() == totalRows) {
            return new ExtendedPosition(new Position(0, pos.getColumn()), cep.getRowMap() + 1, cep.getColumnMap());
        } else if (pos.getColumn() < 0) {
            return new ExtendedPosition(new Position(pos.getRow(), totalCols - 1), cep.getRowMap(),
                    cep.getColumnMap() - 1);
        } else {
            return new ExtendedPosition(new Position(pos.getRow(), 0), cep.getRowMap(), cep.getColumnMap() + 1);
        }
    }

    private boolean isGarden(Position position) {
        return map[position.getRow()][position.getColumn()] != '#';
    }

    private boolean validPosition(Position position) {
        return position.getRow() >= 0 && position.getRow() < totalRows &&
                position.getColumn() >= 0 && position.getColumn() < totalCols;
    }

    private long computeSolution() {
        return currentNodes.size();
    }

    /**
     * rowMap and columnMap represent the field in the infinite field setup. Initially 0,0
     */
    @Data
    private static class ExtendedPosition {
        private final Position position;
        private final int rowMap;
        private final int columnMap;

    }
}

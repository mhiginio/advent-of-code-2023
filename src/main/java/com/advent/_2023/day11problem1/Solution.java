package com.advent._2023.day11problem1;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public long solution(Parser parser) {

        char[][] data = parser.streamLines().map(String::toCharArray).toArray(char[][]::new);

        List<Coords> planets = identifyPlanets(data);
        processEmptyRows(data, planets);
        processEmptyColumns(data, planets);
        return computeDistances(planets);
    }

    public int getGrowth() {
        return 1;
    }

    private void processEmptyRows(char[][] data, List<Coords> planets) {
        for (int i = data.length - 1; i >= 0; i--) {
            char[] row = data[i];
            if (rowIsEmpty(row)) {
                expandRow(planets, i);
            }
        }
    }

    private void expandRow(List<Coords> planets, int i) {
        planets.stream().filter(k -> k.getRow() > i).forEach(k -> k.setRow(k.getRow() + getGrowth()));
    }

    private void processEmptyColumns(char[][] data, List<Coords> planets) {
        for (int j = data[0].length - 1; j >= 0; j--) {
            if (columnIsEmpty(data, j)) {
                expandColumn(planets, j);
            }
        }
    }

    private void expandColumn(List<Coords> planets, int j) {
        planets.stream().filter(k -> k.getColumn() > j).forEach(k -> k.setColumn(k.getColumn() + getGrowth()));
    }

    private boolean columnIsEmpty(char[][] data, int j) {
        for (char[] datum : data) {
            if (datum[j] == '#') {
                return false;
            }
        }
        return true;
    }

    private boolean rowIsEmpty(char[] row) {
        for (char c : row) {
            if (c == '#') {
                return false;
            }
        }
        return true;
    }

    private long computeDistances(List<Coords> planets) {
        long result = 0;
        for (int i = 0; i < planets.size(); i++) {
            Coords planet = planets.get(i);
            for (int j = i + 1; j < planets.size(); j++) {
                result += planet.distance(planets.get(j));
            }
        }
        return result;
    }

    private List<Coords> identifyPlanets(char[][] data) {
        List<Coords> planets = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            char[] row = data[i];
            for (int j = 0; j < row.length; j++) {
                if (row[j] == '#') {
                    planets.add(new Coords(i, j));
                }
            }
        }
        return planets;
    }

    @Data
    @AllArgsConstructor
    public static final class Coords {
        private int row;
        private int column;

        public Coords(Coords coords) {
            row = coords.row;
            column = coords.column;
        }

        public long distance(Coords coords) {
            return Math.abs(row - coords.row) + Math.abs(column - coords.column);
        }
    }
}

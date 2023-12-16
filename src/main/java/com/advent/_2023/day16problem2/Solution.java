package com.advent._2023.day16problem2;

import com.advent._2023.Parser;

import java.util.ArrayList;
import java.util.List;

public class Solution extends com.advent._2023.day16problem1.Solution {

    @Override
    public long solution(Parser parser) {
        initData(parser);
        List<Beam> beams = initialBeams(rows, columns);
        return beams.stream().map(List::of).mapToLong(this::solutionForInitialConfiguration).max().orElse(0);
    }

    public List<com.advent._2023.day16problem1.Solution.Beam> initialBeams(int rows, int columns) {
        List<com.advent._2023.day16problem1.Solution.Beam> result = new ArrayList<>((rows + columns) * 2);
        for (int i = 0; i < rows; i++) {
            result.add(new Beam(new Position(i, 0), Movement.RIGHT));
            result.add(new Beam(new Position(i, columns - 1), Movement.LEFT));
        }
        for (int j = 0; j < columns; j++) {
            result.add(new Beam(new Position(0, j), Movement.DOWN));
            result.add(new Beam(new Position(0, rows - 1), Movement.UP));

        }
        return result;
    }
}
package com.advent._2023.day22.problem2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class Solution extends com.advent._2023.day22.problem1.Solution {
    @Override
    public long findSolution(TreeMap<Integer, List<Brick>> bricks) {
        return bricks.values().stream().flatMap(List::stream).mapToLong(this::countFallen).sum();
    }

    private long countFallen(Brick brick) {
        Set<Brick> fallen = new HashSet<>();
        fallen.add(brick);
        List<Brick> supports = brick.getSupports();
        boolean ended = false;
        while (!ended) {
            List<Brick> newFallen = supports.stream().filter(k -> fallen.containsAll(k.getSupportedBy())).toList();
            fallen.addAll(newFallen);
            supports = newFallen.stream().map(Brick::getSupports).flatMap(List::stream).toList();
            ended = newFallen.isEmpty();
        }
        return fallen.size() - 1;
    }
/*1,0,1~1,2,1   <- A
0,0,2~2,0,2   <- B
0,2,3~2,2,3   <- C
0,0,4~0,2,4   <- D
2,0,5~2,2,5   <- E
0,1,6~2,1,6   <- F
1,1,8~1,1,9   <- G


Disintegrating brick A would cause all 6 other bricks to fall.
Disintegrating brick F would cause only 1 other brick, G, to fall.
*/
}
package com.advent._2023.day22.problem1;

import com.advent._2023.ParseUtils;
import com.advent._2023.Parser;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public long solution(Parser parser) {
        PriorityQueue<Brick> bricks =
                parser.streamLines().map(Brick::parse).collect(Collectors.toCollection(PriorityQueue::new));
        TreeMap<Integer, List<Brick>> finalBrickDistribution = new TreeMap<>();
        while (!bricks.isEmpty()) {
            Brick brick = bricks.poll();
            int height = findFirstCollision(finalBrickDistribution, brick) + 1;
            finalBrickDistribution.computeIfAbsent(height + brick.height(), ArrayList::new).add(brick.moveToZ(height));
        }
        return findSolution(finalBrickDistribution);
    }

    private int findFirstCollision(TreeMap<Integer, List<Brick>> finalBrickDistribution, Brick brick) {
        NavigableSet<Integer> keys = finalBrickDistribution.descendingKeySet();
        for (Integer key : keys) {
            long collisions = collisions(brick, finalBrickDistribution.get(key));
            if (collisions > 0) {
                return key;
            }
        }
        return 0;
    }

    public long findSolution(TreeMap<Integer, List<Brick>> bricks) {
        return bricks.values().stream().flatMap(List::stream).filter(this::canBeRemoved).count();
    }

    private boolean canBeRemoved(Brick brick) {
        return brick.getSupports().stream().noneMatch(k -> k.getSupportedBy().size() == 1);
    }

    private long collisions(Brick brick, List<Brick> bricks) {
        List<Brick> supportedBy = bricks.stream().filter(k -> k.sameHorizontalPlane(brick)).toList();
        if (!supportedBy.isEmpty()) {
            brick.setSupportedBy(supportedBy);
            supportedBy.forEach(k -> k.getSupports().add(brick));
        }
        return supportedBy.size();
    }


    @Data
    @EqualsAndHashCode(of = {"init", "end"})
    @ToString(of = {"init", "end"})
    @RequiredArgsConstructor
    public static final class Brick implements Comparable<Brick> {
        private final Position3D init;
        private final Position3D end;
        private List<Brick> supportedBy;
        private List<Brick> supports = new ArrayList<>();

        public Brick(Brick brick) {
            init = brick.init;
            end = brick.end;
            supports = brick.supports;
            supportedBy = brick.supportedBy;
        }

        private int highestPoint() {
            return Math.max(init.getZ(), end.getZ());
        }

        private int height() {
            int height = end.getZ() - init.getZ();
            if (height < 0) {
                throw new IllegalArgumentException("Something went wrong");
            }
            return height;
        }

        private boolean sameHorizontalPlane(Brick brick) {
            return overlaps(init.getX(), end.getX(), brick.getInit().getX(), brick.getEnd().getX()) &&
                    overlaps(init.getY(), end.getY(), brick.getInit().getY(), brick.getEnd().getY());
        }

        private boolean overlaps(int ini1, int end1, int ini2, int end2) {
            return end1 >= ini2 && ini1 <= end2;
        }

        public static Brick parse(String raw) {
            String[] split = raw.split("~");
            return new Brick(Position3D.parse(split[0]), Position3D.parse(split[1]));
        }

        @Override
        public int compareTo(Brick o) {
            return Integer.compare(highestPoint(), o.highestPoint());
        }

        public Brick moveToZ(int z) {
            Brick brick = new Brick(this);
            int height = brick.height();
            brick.getInit().setZ(z);
            brick.getEnd().setZ(z + height);
            return brick;
        }
    }

    @Data
    @AllArgsConstructor
    private static final class Position3D {
        private int x;
        private int y;
        private int z;

        public Position3D(Position3D position3D) {
            x = position3D.x;
            y = position3D.y;
            z = position3D.z;
        }

        public static Position3D parse(String raw) {
            List<Integer> values = ParseUtils.asIntegerList(raw, ",");
            return new Position3D(values.get(0), values.get(1), values.get(2));
        }
    }
}
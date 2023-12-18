package com.advent._2023.day17problem1;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;


public class Solution {
    private int[][] grid;
    private int rows;
    private int cols;
    private int minMovement;
    private int maxMovement;

    public Solution(int minMovement, int maxMovement) {
        this.minMovement = minMovement;
        this.maxMovement = maxMovement;
    }

    public long solution(Parser parser) {
        grid = parser.streamLines().map(String::toCharArray).map(this::toIntArray).toArray(int[][]::new);
        rows = grid.length;
        cols = grid[0].length;
        return minCostPath();

    }

    private int[] toIntArray(char[] chars) {
        int[] result = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = Character.digit(chars[i], 10);
        }
        return result;
    }


    public int minCostPath() {
        // Create a DP table to store minimum cost to get to a node with a concrete direction
        Map<Node, Integer> dp = new HashMap<>();
        Node initial = new Node(0, 0, null);

        PriorityQueue<HeatNode> queue = new PriorityQueue<>(Comparator.comparing(HeatNode::getHeat));
        queue.add(new HeatNode(initial, 0));
        while (!queue.isEmpty()) {
            HeatNode current = queue.poll();
            if (current.getNode().row() == rows - 1 && current.getNode().column() == cols - 1) {
                System.out.println(current.getPath());
                return current.getHeat();
            }
            if (dp.containsKey(current.getNode()) && dp.get(current.getNode()) <= current.getHeat()) {
                continue;
            }
            processNewNodes(current, queue);
            dp.put(current.getNode(), current.heat);
        }
        return 0;
    }

    private void processNewNodes(HeatNode current, PriorityQueue<HeatNode> queue) {
        for (Direction direction : Direction.values()) {
            for (int i = minMovement; i <= maxMovement; i++) {
                Node newNode = getNewNode(current, direction, i);
                if (validDirection(current, direction) &&
                        newNode.row >= 0 && newNode.row < rows &&
                        newNode.column >= 0 && newNode.column < cols) {
                    HeatNode heatNode = new HeatNode(newNode, getHeat(current, direction, i));
                    heatNode.path.addAll(current.path);
                    heatNode.path.add(newNode);
                    queue.add(heatNode);
                }
            }
        }
    }

    private static boolean validDirection(HeatNode current, Direction direction) {
        Direction currentDirection = current.getNode().direction();
        if (currentDirection == null) {
            return true;
        }
        if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
            return direction == Direction.RIGHT || direction == Direction.LEFT;
        }
        return direction == Direction.UP || direction == Direction.DOWN;
    }

    private int getHeat(HeatNode current, Direction direction, int movement) {
        int result = current.getHeat();
        for (int i = 1; i <= movement; i++) {
            if (direction == Direction.UP) {
                result += grid[current.getNode().row() - i][current.getNode().column()];
            } else if (direction == Direction.DOWN) {
                result += grid[current.getNode().row() + i][current.getNode().column()];
            } else if (direction == Direction.LEFT) {
                result += grid[current.getNode().row()][current.getNode().column() - i];
            } else if (direction == Direction.RIGHT) {
                result += grid[current.getNode().row()][current.getNode().column() + i];
            }
        }
        return result;
    }


    private Node getNewNode(HeatNode current, Direction direction, int movement) {
        Node node = current.getNode();
        switch (direction) {
            case UP -> {
                return new Node(node.row - movement, node.column, Direction.UP);
            }
            case DOWN -> {
                return new Node(node.row + movement, node.column, Direction.DOWN);
            }
            case LEFT -> {
                return new Node(node.row, node.column - movement, Direction.LEFT);
            }
            case RIGHT -> {
                return new Node(node.row, node.column + movement, Direction.RIGHT);
            }
        }
        throw new IllegalArgumentException("Something went wrong");
    }

    private record Node(int row, int column, Direction direction) {
    }

    @Data
    @AllArgsConstructor
    public static class HeatNode {
        private Node node;
        private int heat;
        private final List<Node> path = new ArrayList<>();
    }


    private enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
}
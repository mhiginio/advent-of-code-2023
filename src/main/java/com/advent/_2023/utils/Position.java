package com.advent._2023.utils;

import lombok.Data;

import java.util.List;
import java.util.function.Function;

@Data
public final class Position {
    public static final List<Function<Position, Position>> DIRECTIONS =
            List.of(Position::up, Position::down, Position::left, Position::right);
    private final int row;
    private final int column;

    public Position up() {
        return new Position(row - 1, column);
    }

    public Position down() {
        return new Position(row + 1, column);
    }

    public Position left() {
        return new Position(row, column - 1);
    }

    public Position right() {
        return new Position(row, column + 1);
    }
}
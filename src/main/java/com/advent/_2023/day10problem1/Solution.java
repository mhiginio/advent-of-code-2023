package com.advent._2023.day10problem1;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent._2023.day10problem1.Solution.Movement.*;
import static java.util.function.Function.identity;

public class Solution {
    private char[][] data;

    public long solution(Parser parser) {
        data = parser.streamLines().map(String::toCharArray).toArray(char[][]::new);
        Coords animalCoords = findAnimal();
        Integer cycle = Stream.of(UP, DOWN, LEFT, RIGHT)
                .map(k -> cycleLength(new Coords(animalCoords), k))
                .filter(k -> k != -1).findFirst().orElse(0);
        return cycle / 2;
    }

    private int cycleLength(Coords coords, Movement initialMovement) {
        Pipe currentPipe;
        Movement lastMovement = initialMovement;
        int steps = 0;
        do {
            lastMovement.apply(coords);
            currentPipe = getPipeForCoords(coords);
            lastMovement = currentPipe.nextMovement(lastMovement);
            steps++;
        } while (currentPipe != Pipe.ANIMAL && lastMovement != DEAD_END);
        return currentPipe == Pipe.ANIMAL ? steps : -1;
    }

    private Pipe getPipeForCoords(Coords coords) {
        if (coords.getRow() < 0 || coords.getRow() >= data.length) {
            return Pipe.DOT;
        }
        char[] row = data[coords.getRow()];
        if (coords.getColumn() < 0 || coords.getColumn() >= row.length) {
            return Pipe.DOT;
        }
        return Pipe.getValueFor(row[coords.getColumn()]);
    }


    private Coords findAnimal() {
        for (int i = 0; i < data.length; i++) {
            char[] row = data[i];
            for (int j = 0; j < row.length; j++) {
                if (data[i][j] == 'S') {
                    return new Coords(i, j);
                }
            }
        }
        return null;
    }

    @AllArgsConstructor
    @Getter
    public enum Pipe {
        PIPE('|', m -> {
            switch (m) {
                case UP -> {return UP;}
                case DOWN -> {return DOWN;}
                default -> {return DEAD_END;}
            }
        }),
        SLASH('-', m -> {
            switch (m) {
                case LEFT -> {return LEFT;}
                case RIGHT -> {return RIGHT;}
                default -> {return DEAD_END;}
            }
        }),
        L('L', m -> {
            switch (m) {
                case DOWN -> {return RIGHT;}
                case LEFT -> {return UP;}
                default -> {return DEAD_END;}
            }
        }),
        J('J', m -> {
            switch (m) {
                case DOWN -> {return LEFT;}
                case RIGHT -> {return UP;}
                default -> {return DEAD_END;}
            }
        }),
        F('F', m -> {
            switch (m) {
                case UP -> {return RIGHT;}
                case LEFT -> {return DOWN;}
                default -> {return DEAD_END;}
            }
        }),
        SEVEN('7', m -> {
            switch (m) {
                case UP -> {return LEFT;}
                case RIGHT -> {return DOWN;}
                default -> {return DEAD_END;}
            }
        }),
        DOT('.', m -> DEAD_END),
        ANIMAL('S', m -> DEAD_END);

        private static final Map<Character, Pipe> lookup = Arrays.stream(Pipe.values())
                .collect(Collectors.toMap(Pipe::getValue, identity()));
        private final char value;
        private final Function<Movement, Movement> conversion;

        public static Pipe getValueFor(char c) {
            return lookup.get(c);
        }

        public Movement nextMovement(Movement movement) {
            return conversion.apply(movement);
        }
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
    }

    @AllArgsConstructor
    public enum Movement {
        UP((Coords c) -> c.row--),
        DOWN((Coords c) -> c.row++),
        LEFT((Coords c) -> c.column--),
        RIGHT((Coords c) -> c.column++),
        DEAD_END(null);

        private final Consumer<Coords> consumer;

        public void apply(Coords c) {
            consumer.accept(c);
        }
    }


}

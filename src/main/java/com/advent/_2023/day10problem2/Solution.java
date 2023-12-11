package com.advent._2023.day10problem2;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent._2023.day10problem2.Solution.Movement.*;
import static java.util.function.Function.identity;

public class Solution {
    private char[][] data;
    private Set<Coords> inCycle;

    public long solution(Parser parser) {
        data = parser.streamLines().map(String::toCharArray).toArray(char[][]::new);
        Coords animalCoords = findAnimal();
        Stream.of(UP, DOWN, LEFT, RIGHT)
                .map(k -> cycleLength(new Coords(animalCoords), k))
                .filter(k -> k != -1).findFirst().orElse(0);
        return computeInnerPoints();
    }

    private long computeInnerPoints() {
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            char[] row = data[i];
            for (int j = 0; j < row.length; j++) {
                if (isInnerPoint(new Coords(i, j))) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isInnerPoint(Coords coords) {
        if (inCycle.contains(coords)) {
            return false;
        }
        int count = 0;
        for (int i = coords.getRow() - 1, j = coords.getColumn() - 1; i >= 0 && j >= 0; i--, j--) {
            Coords testCoord = new Coords(i, j);
            if (inCycle.contains(testCoord)) {
                Pipe pipe = getPipeForCoords(testCoord);
                if (pipe != Pipe.SEVEN && pipe != Pipe.L) {
                    count++;
                }
            }
        }
        return count % 2 == 1;
    }

    private int cycleLength(Coords coords, Movement initialMovement) {
        Pipe currentPipe;
        Movement lastMovement = initialMovement;
        int steps = 0;
        inCycle = new HashSet<>();
        do {
            inCycle.add(new Coords(coords));
            lastMovement.apply(coords);
            currentPipe = getPipeForCoords(coords);
            if (currentPipe != Pipe.ANIMAL) {
                lastMovement = currentPipe.nextMovement(lastMovement);
            }
            steps++;
        } while (currentPipe != Pipe.ANIMAL && lastMovement != DEAD_END);
        if (currentPipe == Pipe.ANIMAL) {
            // We should put the real pipe in animal's coordinates
            data[coords.getRow()][coords.getColumn()] = getPipeFromMovements(lastMovement, initialMovement).getValue();
            return steps;
        }
        return -1;
    }

    private Pipe getPipeFromMovements(Movement in, Movement out) {
        if (in == UP && out == UP || in == DOWN && out == DOWN) {
            return Pipe.PIPE;
        }
        if (in == RIGHT && out == RIGHT || in == LEFT && out == LEFT) {
            return Pipe.SLASH;
        }
        if (in == UP && out == RIGHT || in == LEFT && out == DOWN) {
            return Pipe.F;
        }
        if (in == UP && out == LEFT || in == RIGHT && out == DOWN) {
            return Pipe.SEVEN;
        }
        if (in == DOWN && out == LEFT || in == RIGHT && out == UP) {
            return Pipe.J;
        }
        if (in == DOWN && out == RIGHT || in == LEFT && out == UP) {
            return Pipe.L;
        }
        return Pipe.DOT;
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

        private static final Map<Character, Pipe>
                lookup = Arrays.stream(Pipe.values())
                .collect(Collectors.toMap(Pipe::getValue, identity()));
        private final char value;
        private final Function<Movement, Movement>
                conversion;

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

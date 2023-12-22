package com.advent._2023.day16.problem1;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.advent._2023.day16.problem1.Solution.Movement.*;
import static java.util.function.Function.identity;

public class Solution {
    private Tile[][] data;
    protected static int rows;
    protected static int columns;

    public long solution(Parser parser) {
        initData(parser);
        List<Beam> beams = List.of(new Beam(new Position(0, 0), RIGHT));
        return solutionForInitialConfiguration(beams);
    }

    protected void initData(Parser parser) {
        data = parser.streamLines().map(String::toCharArray).map(this::toTileArray).toArray(Tile[][]::new);
        rows = data.length;
        columns = data[0].length;
    }

    protected long solutionForInitialConfiguration(List<Beam> beams) {
        resetData();
        while (!beams.isEmpty()) {
            beams = processElements(beams);
        }
        return computeSolution();
    }

    private void resetData() {
        for (Tile[] row : data) {
            for (Tile tile : row) {
                tile.resetTraversed();
            }
        }
    }

    private List<Beam> processElements(List<Beam> beams) {
        List<Beam> result = new ArrayList<>();
        for (Beam beam : beams) {
            Tile tile = getTile(beam.getPosition());
            if (!alreadyProcessed(tile, beam)) {
                tile.getTraversed().add(beam.getMovement());
                Element element = Element.getValueFor(tile.getContent());
                element.getConversion().apply(beam.getMovement()).stream()
                        .map(k -> new Beam(beam.getPosition(), k))
                        .map(Beam::move)
                        .filter(Beam::isValid)
                        .forEach(result::add);
            }
        }
        return result;
    }

    private static boolean alreadyProcessed(Tile tile, Beam beam) {
        return tile.getTraversed().contains(beam.getMovement());
    }

    private Tile getTile(Position position) {
        return data[position.getRow()][position.getColumn()];
    }

    private Tile[] toTileArray(char[] chars) {
        Tile[] result = new Tile[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = new Tile(chars[i]);
        }
        return result;
    }

    private long computeSolution() {
        long result = 0;
        for (Tile[] row : data) {
            for (Tile tile : row) {
                if (!tile.getTraversed().isEmpty()) {
                    result++;
                }
            }
        }
        return result;
    }

    @Data
    private static class Tile {
        private final char content;
        private Set<Movement> traversed = new HashSet<>();

        public void resetTraversed() {
            traversed = new HashSet<>();
        }
    }

    @Data
    @AllArgsConstructor
    public static class Beam {
        private Position position;
        private Movement movement;

        public Beam move() {
            position = new Position(position);
            movement.apply(position);
            return this;
        }

        public boolean isValid() {
            return position.getRow() >= 0 && position.getRow() < rows && position.getColumn() >= 0 &&
                    position.getColumn() < columns;

        }
    }


    @AllArgsConstructor
    @Getter
    // @formatter:off
    public enum Element {
        PIPE('|', m -> {
            switch (m) {
                case UP -> {return List.of(UP);}
                case DOWN -> {return List.of(DOWN);}
                default -> {return List.of(UP, DOWN);}
            }
        }),
        SLASH('-', m -> {
            switch (m) {
                case LEFT -> {return List.of(LEFT);}
                case RIGHT -> {return List.of(RIGHT);}
                default -> {return List.of(LEFT, RIGHT);}
            }
        }),
        FORWARD_MIRROR('/', m -> {
            switch (m) {
                case DOWN -> {return List.of(LEFT);}
                case LEFT -> {return List.of(DOWN);}
                case UP -> {return List.of(RIGHT);}
                case RIGHT -> {return List.of(UP);}
                default -> {return null;}
            }
        }),
        BACKWARD_MIRROR('\\', m -> {
            switch (m) {
                case DOWN -> {return List.of(RIGHT);}
                case LEFT -> {return List.of(UP);}
                case UP -> {return List.of(LEFT);}
                case RIGHT -> {return List.of(DOWN);}
                default -> {return null;}
            }
        }),
        DOT('.', List::of);
        // @formatter:on

        private static final Map<Character, Element>
                lookup = Arrays.stream(Element.values())
                .collect(Collectors.toMap(Element::getValue, identity()));
        private final char value;
        private final Function<Movement, List<Movement>>
                conversion;

        public static Element getValueFor(char c) {
            return lookup.get(c);
        }

        public List<Movement> nextMovement(Movement movement) {
            return conversion.apply(movement);
        }
    }

    @Data
    @AllArgsConstructor
    public static final class Position {
        private int row;
        private int column;

        public Position(Position position) {
            row = position.row;
            column = position.column;
        }
    }

    @AllArgsConstructor
    public enum Movement {
        UP((Position c) -> c.row--),
        DOWN((Position c) -> c.row++),
        LEFT((Position c) -> c.column--),
        RIGHT((Position c) -> c.column++),
        DEAD_END(null);

        private final Consumer<Position> consumer;

        public void apply(Position c) {
            consumer.accept(c);
        }
    }


}
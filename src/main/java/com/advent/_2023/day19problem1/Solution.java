package com.advent._2023.day19problem1;

import com.advent._2023.Parser;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;


/**
 * This uses the shoelace formula and Pick's theorem
 */
public class Solution {


    public long solution(Parser parser) {
        InputProcessor inputProcessor = new InputProcessor();
        parser.streamLines().forEach(inputProcessor::processLine);
        return inputProcessor.getParts().stream().mapToLong(k -> processPart(k, inputProcessor.getWorkflows())).sum();
    }

    private long processPart(Part part, Map<String, List<Condition>> workflows) {
        String currentWorkflow = "in";
        while (true) {
            List<Condition> conditions = workflows.get(currentWorkflow);
            boolean conditionMatched = false;
            for (int i = 0; i < conditions.size() && !conditionMatched; i++) {
                Condition condition = conditions.get(i);
                Status conditionResult = condition.process(part);
                if (conditionResult.getAccepted() != null) {
                    return conditionResult.getAccepted() ? part.getValue() : 0;
                } else if (conditionResult.getNextWorkflow() != null) {
                    currentWorkflow = conditionResult.getNextWorkflow();
                    conditionMatched = true;
                }
            }
        }
    }

    @Data
    private static final class Condition {
        private static final Map<String, Function<Part, Integer>> GETTERS = Map.of(
                "x", Part::getX, "m", Part::getM, "a", Part::getA, "s", Part::getS
        );
        private static final Map<String, BiFunction<Integer, Integer, Boolean>> COMPARATORS = Map.of(
                "<", (x, y) -> x < y, ">", (x, y) -> x > y
        );

        private Function<Part, Integer> getter;
        private String action;
        private BiFunction<Integer, Integer, Boolean> comparator;
        private Integer value;


        public Status process(Part part) {
            if (comparator == null) {
                return Status.of(action);
            } else {
                return Status.of(comparator.apply(getter.apply(part), value) ? action : null);
            }
        }

        public static Condition parseCondition(String rawCondition) {
            Condition condition = new Condition();
            String[] split = rawCondition.split(":");
            if (split.length == 1) {
                // Unconditional action
                condition.setAction(split[0]);
            } else {
                condition.setGetter(GETTERS.get(split[0].substring(0, 1)));
                condition.setComparator(COMPARATORS.get(split[0].substring(1, 2)));
                condition.setValue(Integer.parseInt(split[0].substring(2)));
                condition.setAction(split[1]);
            }
            return condition;
        }
    }

    @Data
    private static final class Status {
        private static final String ACCEPTED = "A";
        private static final String REJECTED = "R";
        private Boolean accepted;
        private String nextWorkflow;

        public static Status of(String action) {
            Status status = new Status();
            if (ACCEPTED.equals(action)) {
                status.setAccepted(true);
            } else if (REJECTED.equals(action)) {
                status.setAccepted(false);
            } else {
                status.setNextWorkflow(action);
            }
            return status;
        }
    }

    @Data
    @Setter
    @NoArgsConstructor
    public static final class Part {
        private int x;
        private int m;
        private int a;
        private int s;

        private static Part readPart(String rawLine) {
            Part part = new Part();
            String[] split = rawLine.split("=");
            part.setX(Integer.parseInt(split[1].substring(0, split[1].indexOf(','))));
            part.setM(Integer.parseInt(split[2].substring(0, split[2].indexOf(','))));
            part.setA(Integer.parseInt(split[3].substring(0, split[3].indexOf(','))));
            part.setS(Integer.parseInt(split[4].substring(0, split[4].indexOf('}'))));
            return part;
        }

        public long getValue() {
            return x + m + a + s;
        }
    }

    private static final class InputProcessor {
        private boolean readingWorkflows = true;
        @Getter
        private final Map<String, List<Solution.Condition>> workflows = new HashMap<>();
        @Getter
        private final List<Part> parts = new ArrayList<>();

        public void processLine(String rawLine) {
            if (rawLine.isEmpty()) {
                readingWorkflows = false;
            } else if (readingWorkflows) {
                readWorkflow(rawLine);
            } else {
                parts.add(Part.readPart(rawLine));
            }
        }

        private void readWorkflow(String rawLine) {
            String name = rawLine.substring(0, rawLine.indexOf('{'));
            String[] rawConditions = rawLine.substring(rawLine.indexOf('{') + 1, rawLine.length() - 1).split(",");
            workflows.put(name, Arrays.stream(rawConditions).map(Condition::parseCondition).toList());
        }
    }
}
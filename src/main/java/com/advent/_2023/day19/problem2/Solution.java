package com.advent._2023.day19.problem2;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;


public class Solution {


    public long solution(Parser parser) {
        InputProcessor inputProcessor = new InputProcessor();
        parser.streamLines().forEach(inputProcessor::processLine);
        Map<String, Range> solutions = new HashMap<>();
        String[] values = new String[]{"x", "m", "a", "s"};
        Arrays.stream(values).forEach(k -> solutions.put(k, new Range()));
        SolutionRange solutionRange = new SolutionRange(solutions, "in");
        return processSolutions(solutionRange, inputProcessor.getWorkflows());
    }

    private long processSolutions(SolutionRange solutionRange, Map<String, List<Condition>> workflows) {
        Queue<SolutionRange> solutionRanges = new LinkedList<>();
        solutionRanges.add(solutionRange);

        long result = 0;
        while (!solutionRanges.isEmpty()) {
            SolutionRange currentSolutionRange = solutionRanges.poll();
            List<Condition> conditions = workflows.get(currentSolutionRange.getCurrentWorkflow());
            boolean endedConditions = false;
            Condition previousCondition = null;
            for (int i = 0; i < conditions.size() && !endedConditions; i++) {
                Condition condition = conditions.get(i);
                String action = condition.getAction();
                if (previousCondition != null) {
                    currentSolutionRange.negate(previousCondition);
                }
                if (currentSolutionRange.size() == 0) {
                    endedConditions = true;
                } else {
                    if (condition.getComparator() == null) {
                        if ("A".equals(action)) {
                            result += currentSolutionRange.size();
                        } else if (!"R".equals(action)) {
                            currentSolutionRange.setCurrentWorkflow(action);
                            solutionRanges.add(currentSolutionRange);
                        }
                        endedConditions = true;
                    } else {
                        SolutionRange newSolutionRange = currentSolutionRange.apply(condition);
                        if ("A".equals(action)) {
                            result += newSolutionRange.size();
                        } else if (!"R".equals(action)) {
                            newSolutionRange.setCurrentWorkflow(action);
                            solutionRanges.add(newSolutionRange);
                        }
                        previousCondition = condition;
                    }
                }
            }
        }
        return result;

    }

    @AllArgsConstructor
    @Data
    private static class SolutionRange {
        private Map<String, Range> solutions;
        private String currentWorkflow;

        public SolutionRange(SolutionRange solutionRange) {
            currentWorkflow = solutionRange.getCurrentWorkflow();
            solutions = solutionRange.getSolutions().entrySet().stream().collect(
                    Collectors.toMap(Map.Entry::getKey, k -> new Range(k.getValue().getMin(), k.getValue().getMax())));
        }

        public long size() {
            return solutions.values().stream().mapToLong(Range::size).reduce(1L, (a, b) -> a * b);
        }

        public void negate(Condition condition) {
            String variable = condition.getVariable();
            Range range = solutions.get(variable);
            solutions.put(variable, range.negate(condition));
        }

        public SolutionRange apply(Condition condition) {
            SolutionRange result = new SolutionRange(this);
            String variable = condition.getVariable();
            Range range = result.getSolutions().get(variable);
            result.getSolutions().put(variable, range.apply(condition));
            return result;
        }
    }

    @Data
    public static final class Condition {

        private String variable;
        private String action;
        private String comparator;
        private Integer value;

        public static Condition parseCondition(String rawCondition) {
            Condition condition = new Condition();
            String[] split = rawCondition.split(":");
            if (split.length == 1) {
                // Unconditional action
                condition.setAction(split[0]);
            } else {
                condition.setVariable(split[0].substring(0, 1));
                condition.setComparator(split[0].substring(1, 2));
                condition.setValue(Integer.parseInt(split[0].substring(2)));
                condition.setAction(split[1]);
            }
            return condition;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Range {
        private int min = 1;
        private int max = 4000;


        public long size() {
            return max >= min ? max - min + 1 : 0;
        }

        public Range negate(Condition condition) {
            Range result;
            Integer value = condition.getValue();
            if ("<".equals(condition.getComparator())) {
                // new range must be >= value
                result = new Range(Math.max(min, value), max);
            } else {
                // new range must be <= value
                result = new Range(min, Math.min(max, value));
            }
            return result;
        }

        public Range apply(Condition condition) {
            Range result;
            Integer value = condition.getValue();
            if ("<".equals(condition.getComparator())) {
                // new range must be < value
                result = new Range(min, Math.min(max, value - 1));
            } else {
                // new range must be > value
                result = new Range(Math.max(min, value + 1), max);
            }
            return result;
        }
    }

    @Getter
    private static final class InputProcessor {

        private final Map<String, List<Condition>> workflows = new HashMap<>();

        public void processLine(String rawLine) {
            if (!rawLine.isEmpty()) {
                readWorkflow(rawLine);
            }
        }

        private void readWorkflow(String rawLine) {
            String name = rawLine.substring(0, rawLine.indexOf('{'));
            String[] rawConditions = rawLine.substring(rawLine.indexOf('{') + 1, rawLine.length() - 1).split(",");
            workflows.put(name, Arrays.stream(rawConditions).map(Condition::parseCondition).toList());
        }
    }
}
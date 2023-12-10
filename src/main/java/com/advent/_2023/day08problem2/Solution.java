package com.advent._2023.day08problem2;

import com.advent._2023.Parser;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public long solution(Parser parser) {
        List<String> lines = parser.streamLines().toList();
        LRRule lrRule = new LRRule(lines.get(0));
        Map<String, Mapping> mappings = buildMappings(lines.subList(2, lines.size()));
        return findSolution(lrRule, mappings);
    }

    private Map<String, Mapping> buildMappings(List<String> rawMappings) {
        Map<String, Mapping> result = new HashMap<>();
        for (String rawMapping : rawMappings) {
            result.put(rawMapping.substring(0, 3),
                    new Mapping(rawMapping.substring(7, 10), rawMapping.substring(12, 15)));
        }
        return result;
    }

    private long findSolution(LRRule lrRule, Map<String, Mapping> mappings) {
        List<String> currentStates = mappings.keySet().stream()
                .filter(k -> k.endsWith("A"))
                .toList();
        // For some unfair reason the input was built in a way that each ghost reaches his end state every x steps,
        // So the solution is the lowest common multiple of these cycles.
        List<Long> cycles = currentStates.stream()
                .map(k -> findLoopSize(k, lrRule, mappings))
                .toList();
        return LCMCalculator.compute(cycles);
    }

    private long findLoopSize(String state, LRRule lrRule, Map<String, Mapping> mappings) {
        int steps = 0;
        String current = state;
        lrRule.reset();
        do {
            char rule = lrRule.next();
            current = rule == 'L' ? mappings.get(current).left() : mappings.get(current).right();
            steps++;
        } while (!current.endsWith("Z"));
        return steps;
    }

    @Data
    public static final class LRRule {
        private final char[] rules;
        private int index;

        public LRRule(String content) {
            rules = content.toCharArray();
            index = 0;
        }

        public char next() {
            char next = rules[index];
            index = index + 1;
            if (index == rules.length) {
                index = 0;
            }
            return next;
        }

        public void reset() {
            index = 0;
        }
    }

    private record Mapping(String left, String right) {
    }

    private static final class LCMCalculator {
        public static long compute(List<Long> values) {
            long lcm = values.get(0);
            long gcd = values.get(0);

            //loop through the array to find GCD
            //use GCD to find the LCM
            for (int i = 1; i < values.size(); i++) {
                gcd = findGCD(values.get(i), lcm);
                lcm = (lcm * values.get(i)) / gcd;
            }

            return lcm;
        }

        public static long findGCD(long a, long b) {
            //base condition
            if (b == 0)
                return a;

            return findGCD(b, a % b);
        }
    }
}

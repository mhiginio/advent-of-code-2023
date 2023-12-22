package com.advent._2023.day08.problem1;

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
        int steps = 0;
        String current = "AAA";
        do {
            char rule = lrRule.next();
            current = rule == 'L' ? mappings.get(current).left() : mappings.get(current).right();
            steps++;
        } while (!current.equals("ZZZ"));
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
    }

    private record Mapping(String left, String right) {
    }
}

package com.advent._2023.day12problem1;

import com.advent._2023.ParseUtils;
import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Solution {
    public long solution(Parser parser) {
        return parser.streamLines().map(ConditionReport::parse).mapToLong(this::solution).sum();
    }

    public long solution(ConditionReport conditionReport) {
        int totalDamaged = conditionReport.getDamagedSpringSizes().stream().mapToInt(Integer::intValue).sum();
        int damagedToDistribute = totalDamaged - conditionReport.getKnownDamaged();
        List<Integer> unknownPositions = conditionReport.getQuestionMarks();

        TokenDistributor tokenDistributor = new TokenDistributor(unknownPositions,
                damagedToDistribute,
                conditionReport);
        tokenDistributor.distribute();
        return tokenDistributor.getCount();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class ConditionReport {
        private char[] springData;
        private List<Integer> damagedSpringSizes;

        public static ConditionReport parse(String rawData) {
            ConditionReport result = new ConditionReport();
            String[] split = rawData.split(" ");
            result.setSpringData(split[0].toCharArray());
            result.setDamagedSpringSizes(ParseUtils.asIntegerList(split[1], ","));
            return result;
        }

        public List<Integer> getQuestionMarks() {
            return IntStream.range(0, springData.length).filter(k -> springData[k] == '?').boxed().toList();
        }

        public int getKnownDamaged() {
            return (int) IntStream.range(0, springData.length).filter(k -> springData[k] == '#').count();
        }
    }

    /**
     * Generates all possible combinations from a list of n elements picking m of them each time.
     * Passes all combinations to a DistributionProcessor
     */
    @Data
    @RequiredArgsConstructor
    public static class TokenDistributor {
        private final List<Integer> tokens;
        private final int size;
        private final ConditionReport conditionReport;
        private long count = 0;

        public void distribute() {
            computeDistributions(0, size, new ArrayList<>());
        }

        private void computeDistributions(int start,
                                          int damagedToDistribute,
                                          List<Integer> current) {
            if (damagedToDistribute == 0) {
                process(new ArrayList<>(current));
            }
            for (int i = start; i < tokens.size(); i++) {
                current.add(tokens.get(i));
                if (preCheckDistribution(current, tokens.get(i) + 1)) {
                    computeDistributions(i + 1, damagedToDistribute - 1, current);
                }
                current.remove(current.size() - 1);
            }
        }

        public void process(List<Integer> distribution) {
            if (damagedSpringsDistribution(distribution).equals(conditionReport.getDamagedSpringSizes())) {
                count++;
            }
        }

        public boolean preCheckDistribution(List<Integer> current, int until) {
            List<Integer> preAssignments = damagedSpringsDistribution(current, until);
            List<Integer> damagedSpringSizes = conditionReport.getDamagedSpringSizes();
            if (preAssignments.size() > damagedSpringSizes.size()) {
                return false;
            }
            preAssignments.remove(preAssignments.size() - 1); // Last element can be not definitive
            for (int i = 0; i < preAssignments.size(); i++) {
                if (!preAssignments.get(i).equals(damagedSpringSizes.get(i))) {
                    return false;
                }

            }
            return true;
        }


        /**
         * Counts the consecutive damagedSpring having that the indexes in distribution are damaged springs as well
         */
        private List<Integer> damagedSpringsDistribution(List<Integer> distribution, int until) {
            int currentSize = 0;
            List<Integer> result = new ArrayList<>();
            char[] data = conditionReport.getSpringData();
            for (int i = 0; i < until; i++) {
                if (data[i] == '#' || distribution.contains(i)) {
                    currentSize++;
                } else if (currentSize > 0) {
                    result.add(currentSize);
                    currentSize = 0;
                }
            }
            if (currentSize > 0) {
                result.add(currentSize);
            }
            return result;
        }

        /**
         * Counts the consecutive damagedSpring having that the indexes in distribution are damaged springs as well
         */
        private List<Integer> damagedSpringsDistribution(List<Integer> distribution) {
            return damagedSpringsDistribution(distribution, conditionReport.getSpringData().length);
        }
    }

}

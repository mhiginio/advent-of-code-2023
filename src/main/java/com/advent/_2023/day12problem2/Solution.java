package com.advent._2023.day12problem2;

import com.advent._2023.ParseUtils;
import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class Solution {
    public long solution(Parser parser) {
        return parser.streamLines().map(this::unfold).map(ConditionReport::parse).mapToLong(this::solution).sum();
    }

    private String unfold(String rawData) {
        StringBuilder builder = new StringBuilder();
        String[] split = rawData.split(" ");
        for (int i = 0; i < 4; i++) {
            builder.append(split[0]).append('?');

        }
        builder.append(split[0]);
        builder.append(' ');
        for (int i = 0; i < 4; i++) {
            builder.append(split[1]).append(',');

        }
        builder.append(split[1]);
        return builder.toString();
    }

    public long solution(ConditionReport conditionReport) {
        boolean[] damaged = conditionReport.expandDamaged();
        return countArrangements(conditionReport.getSpringData(), damaged);
    }


    /**
     * dp[i][j] = how many arrangements when matching chars[i..n-1] with springs[j..m-1]
     * dp[i][j] = match(chars[i]):
     * # => if(s[j] == T) dp[i+1][j+1] (you have to match one damaged spring)
     * else 0
     * . => if(s[j] == F) dp[i+1][j+1] + dp[i+1][j]  (you can either match the whole gap or match more operational inside the same gap)
     * else 0
     * ? => you can replace it to . or # so it's the sum of both
     * springs for 1,1,3, for example, is expanded to [T, F, T, F, T, T, T]
     */
    private static long countArrangements(char[] chars, boolean[] springs) {
        int n = chars.length;
        int m = springs.length;
        long[][] dp = new long[n + 1][m + 1];
        dp[n][m] = 1;

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                boolean damaged = false, operational = false;
                switch (chars[i]) {
                    case '#': {
                        damaged = true;
                        break;
                    }
                    case '.': {
                        operational = true;
                        break;
                    }
                    default: {
                        operational = true;
                        damaged = true;
                    }
                }
                long sum = 0;
                if (damaged && springs[j]) {
                    sum += dp[i + 1][j + 1];
                } else if (operational && !springs[j]) {
                    sum += dp[i + 1][j + 1] + dp[i + 1][j];
                }
                dp[i][j] = sum;
            }
        }
        return dp[0][0];
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class ConditionReport {
        private char[] springData;
        private List<Integer> damagedSpringSizes;

        public static ConditionReport parse(String rawData) {
            ConditionReport
                    result = new ConditionReport();
            String[] split = rawData.split(" ");
            result.setSpringData(('.' + split[0]+ '.').toCharArray());
            result.setDamagedSpringSizes(ParseUtils.asIntegerList(split[1], ","));
            return result;
        }

        private boolean[] expandDamaged() {
            boolean[] result = new boolean[
                    damagedSpringSizes.stream().mapToInt(Integer::intValue).sum() + damagedSpringSizes.size() + 1];
            int idx = 0;
            result[idx++] = false;
            for (Integer size : damagedSpringSizes) {
                for (int i = 0; i < size; i++) {
                    result[idx++] = true;
                }
                result[idx++] = false;
            }
            return result;
        }
    }

}
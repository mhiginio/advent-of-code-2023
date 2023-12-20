package com.advent._2023;

import java.util.List;

public final class LCMCalculator {
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
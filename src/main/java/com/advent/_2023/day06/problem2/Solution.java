package com.advent._2023.day06.problem2;

import com.advent._2023.Parser;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class Solution {
    public long solution(Parser parser) {
        List<String> lines = parser.streamLines().toList();
        Long time = Long.parseLong(lines.get(0).split(":")[1].replaceAll(" ", ""));
        Long distance = Long.parseLong(lines.get(1).split(":")[1].replaceAll(" ", ""));
        return computeSolution(time, distance);
    }

    private int computeSolution(Long time, Long distance) {
        /*
        Having n = the time the button is pressed (between 0 and time) the distance is equal to: n*(n-7)
        The time the button was pressed to reach the distance 'distance' is determined by the solution to the
        quadratic equation:
             n*(time-n)=distance --> -n^2 + time*n - distance = 0
        All the solutions between these two values are better
         */
        List<Double> times = quadraticEquationRoots(-1, +time, -distance);
        int solutions = (int) Math.floor(times.get(1)) - (int) Math.ceil(times.get(0)) + 1;
        if (Math.floor(times.get(1)) == times.get(1)) {
            solutions--;
        }
        if (Math.round(times.get(0)) == times.get(0)) {
            solutions--;
        }
        return solutions;
    }

    public static void main(String[] args) {
        System.out.println(quadraticEquationRoots(1, -7, 9));
    }

    public static List<Double> quadraticEquationRoots(double a, double b, double c) {
        List<Double> result = new ArrayList<>();
        double discriminant = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
        if (discriminant == 0) {
            result.add(-b / (2 * a));
        } else if (discriminant > 0) {
            result.add((-b + discriminant) / (2 * a));
            result.add((-b - discriminant) / (2 * a));
        }
        sort(result);
        return result;
    }
}
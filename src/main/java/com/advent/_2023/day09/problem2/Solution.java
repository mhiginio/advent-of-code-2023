package com.advent._2023.day09.problem2;

import com.advent._2023.ParseUtils;
import com.advent._2023.Parser;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public long solution(Parser parser) {
        return parser.streamLines().mapToInt(this::extrapolationForSingleSeries).sum();
    }

    private int extrapolationForSingleSeries(String series) {
        List<Integer> integerList = ParseUtils.asIntegerList(series);
        List<List<Integer>> theSeriesTower = new ArrayList<>();
        List<Integer> reducedList = integerList;
        do {
            theSeriesTower.add(reducedList);
            reducedList = computeDifferences(reducedList);
        } while (reducedList.stream().anyMatch(k -> k != 0));
        return extrapolatePreviousValue(theSeriesTower);
    }

    private List<Integer> computeDifferences(List<Integer> originalList) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < originalList.size(); i++) {
            result.add(originalList.get(i) - originalList.get(i - 1));
        }
        return result;
    }

    private int extrapolatePreviousValue(List<List<Integer>> theSeriesTower) {
        int result = 0;
        for (int i = theSeriesTower.size() - 1; i >= 0; i--) {
            result = theSeriesTower.get(i).get(0) - result;
        }
        return result;
    }
}

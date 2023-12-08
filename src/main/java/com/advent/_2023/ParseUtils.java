package com.advent._2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParseUtils {

    public static List<Integer> asIntegerList(String rawData) {
        List<Integer> result = new ArrayList<>();
        Scanner scanner = new Scanner(rawData);
        while (scanner.hasNext()) {
            result.add(scanner.nextInt());
        }
        return result;
    }

    public static List<Long> asLongList(String rawData) {
        List<Long> result = new ArrayList<>();
        Scanner scanner = new Scanner(rawData);
        while (scanner.hasNext()) {
            result.add(scanner.nextLong());
        }
        return result;
    }
}

package com.advent._2023.day15.problem2;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

import static java.util.Arrays.stream;

public class Solution extends com.advent._2023.day15.problem1.Solution {
    private final Map<Integer, LensCollection> boxes = new HashMap<>();

    public long solution(Parser parser) {
        String[] instructions = parser.streamLines().map(k -> k.split(",")).findFirst().orElseThrow();
        stream(instructions).forEach(this::processInstruction);
        return computeFocusingPower();
    }

    private void processInstruction(String instruction) {
        if (instruction.endsWith("-")) {
            processRemoval(instruction);
        } else {
            processUpsert(instruction);
        }
    }

    private void processRemoval(String instruction) {
        String label = instruction.substring(0, instruction.length() - 1);
        boxes.computeIfAbsent(hash(label), x -> new LensCollection()).removeByLabel(label);
    }

    private void processUpsert(String instruction) {
        int idx = instruction.indexOf('=');
        String label = instruction.substring(0, idx);
        int focalLength = Integer.parseInt(instruction.substring(idx + 1));
        boxes.computeIfAbsent(hash(label), x -> new LensCollection()).upsert(label, focalLength);
    }

    private long computeFocusingPower() {
        return boxes.entrySet().stream().mapToInt(e -> e.getValue().computeFocusingPower(e.getKey())).sum();
    }

    @Data
    public static final class LensCollection {
        private final List<Lens> lens = new LinkedList<>();

        public void removeByLabel(String label) {
            lens.remove(Lens.of(label));
        }

        public void upsert(String label, int focalLength) {
            Lens currentLens = Lens.of(label, focalLength);
            int idx = lens.indexOf(currentLens);
            if (idx != -1) {
                lens.get(idx).setFocalLength(focalLength);
            } else {
                lens.add(currentLens);
            }
        }

        public int computeFocusingPower(Integer boxNumber) {
            int result = 0;
            for (int i = 0; i < lens.size(); i++) {
                result += (boxNumber + 1) * (i + 1) * lens.get(i).getFocalLength();
            }
            return result;
        }
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(of = "label")
    public static final class Lens {
        private final String label;
        private int focalLength;

        public static Lens of(String label) {
            return of(label, 0);
        }

        public static Lens of(String label, int focalLength) {
            return new Lens(label, focalLength);
        }
    }
}
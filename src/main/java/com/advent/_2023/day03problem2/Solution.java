package com.advent._2023.day03problem2;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int solution(Parser parser) {
        Schematic schematic = new Schematic(parser.streamLines().map(String::toCharArray).toList());
        List<SchematicNumber> schematicNumbers = schematic.getSchematicNumbers();
        List<Coords> gearCandidates = schematic.getGearCandidates();
        return gearCandidates.stream().mapToInt(k -> getGearPower(schematicNumbers, k)).sum();
    }

    private int getGearPower(List<SchematicNumber> schematicNumbers, Coords gear) {
        List<SchematicNumber> list = schematicNumbers.stream().filter(k -> adjacents(k, gear)).toList();
        if (list.size() != 2) {
            return 0;
        }
        return list.get(0).getNumber() * list.get(1).getNumber();
    }

    private boolean adjacents(SchematicNumber schematicNumber, Coords gear) {
        return gear.getX() >= schematicNumber.getCoords().getX() - 1 &&
                gear.getX() <= schematicNumber.getCoords().getX() + schematicNumber.getLength() &&
                gear.getY() >= schematicNumber.getCoords().getY() - 1 &&
                gear.getY() <= schematicNumber.getCoords().getY() + 1;
    }


    @AllArgsConstructor
    @Getter
    public static class Schematic {
        private List<char[]> lines;

        List<SchematicNumber> getSchematicNumbers() {
            int y = 0;
            List<SchematicNumber> result = new ArrayList<>();
            do {
                int x = 0;
                char[] line = lines.get(y);
                do {
                    if (Character.isDigit(line[x])) {
                        SchematicNumber schematicNumber = getSchematicNumber(line, x, y);
                        result.add(schematicNumber);
                        x += schematicNumber.length;
                    } else {
                        x++;
                    }
                } while (x < line.length);
                y++;
            } while (y < lines.size());
            return result;
        }

        private SchematicNumber getSchematicNumber(char[] line, int x, int y) {
            SchematicNumber result = new SchematicNumber();
            result.setCoords(new Coords(x, y));
            int length = 0;
            int number = 0;
            while (x < line.length && Character.isDigit(line[x])) {
                number = number * 10 + line[x++] - '0';
                length++;
            }
            result.setNumber(number);
            result.setLength(length);
            return result;
        }

        public List<Coords> getGearCandidates() {
            List<Coords> result = new ArrayList<>();
            for (int y = 0; y < lines.size(); y++) {
                char[] line = lines.get(y);
                for (int x = 0; x < line.length; x++) {
                    if (line[x] == '*') {
                        result.add(new Coords(x, y));
                    }
                }
            }
            return result;
        }
    }

    @Data
    @NoArgsConstructor
    public static class SchematicNumber {
        private int number;
        private Coords coords;
        private int length;
    }

    @Data
    @AllArgsConstructor
    public static class Coords {
        private int x;
        private int y;
    }
}
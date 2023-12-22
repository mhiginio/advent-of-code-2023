package com.advent._2023.day03.problem1;

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
        return schematicNumbers.stream().filter(k -> isValid(schematic, k)).mapToInt(SchematicNumber::getNumber).sum();
    }

    private boolean isValid(Schematic schematic, SchematicNumber schematicNumber) {
        int y = schematicNumber.getY();
        for (int x = schematicNumber.getX() - 1; x < schematicNumber.getX() + schematicNumber.getLength() + 1; x++) {
            if (schematic.isSymbol(x, y - 1) || schematic.isSymbol(x, y + 1)) {
                return true;
            }
        }
        if (schematic.isSymbol(schematicNumber.getX() - 1, y) ||
                schematic.isSymbol(schematicNumber.getX() + schematicNumber.getLength(), y)) {
            return true;
        }
        return false;
    }

    @AllArgsConstructor
    @Getter
    public static class Schematic {
        private List<char[]> lines;

        public List<SchematicNumber> getSchematicNumbers() {
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
            result.setY(y);
            result.setX(x);
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

        public boolean isSymbol(int x, int y) {
            if (y < 0 || y >= lines.size()) {
                return false;
            }
            char[] line = lines.get(y);
            if (x < 0 || x >= line.length) {
                return false;
            }
            return line[x] != '.' && !Character.isDigit(line[x]);
        }
    }

    @Data
    @NoArgsConstructor
    public static class SchematicNumber {
        private int number;
        private int x;
        private int y;
        private int length;
    }
}
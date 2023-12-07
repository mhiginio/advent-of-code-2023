package com.advent._2023;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class InputParser implements Parser {
    private BufferedReader reader;

    public InputParser(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream));
    }

    @Override
    public Stream<String> streamLines() {
        return reader.lines();
    }
}

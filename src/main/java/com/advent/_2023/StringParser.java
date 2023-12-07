package com.advent._2023;

import java.util.stream.Stream;

import static java.util.stream.Stream.of;

public class StringParser implements Parser {
    private final String[] content;

    public StringParser(String[] content) {
        this.content = content;
    }

    @Override
    public Stream<String> streamLines() {
        return of(content);
    }
}

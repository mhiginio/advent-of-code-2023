package com.advent._2023.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class QuadraticFunctionTest {


    @ParameterizedTest
    @MethodSource("com.advent._2023.utils.QuadraticFunctionTest#testData")
    void should_return_correct_values(int x1, int y1, int x2, int y2, int x3, int y3, double[] expected) {
        double[] quadraticFunction = QuadraticFunction.findQuadraticFunction(x1, y1, x2, y2, x3, y3);
        assertArrayEquals(expected, quadraticFunction);
    }

    public static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.arguments(0, 1, 1, 3, 2, 7, new double[]{1, 1, 1}),
                Arguments.arguments(-1, 3, 0, 4, 1, 9, new double[]{2, 3, 4})
        );
    }
}
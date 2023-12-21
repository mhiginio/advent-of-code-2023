package com.advent._2023.utils;

public class QuadraticFunction {

    // Method to find coefficients of the quadratic equation
    public static double[] findQuadraticFunction(double x1, double y1, double x2, double y2, double x3, double y3) {
        double[][] matrix = {{x1 * x1, x1, 1}, {x2 * x2, x2, 1}, {x3 * x3, x3, 1}};
        double[] constants = {y1, y2, y3};

        // Solving the simultaneous equations
        return solveEquations(matrix, constants);
    }

    // Method to solve simultaneous equations using Cramer's rule
    private static double[] solveEquations(double[][] matrix, double[] constants) {
        double detMatrix = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[2][1] * matrix[1][2])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

        double detX = constants[0] * (matrix[1][1] * matrix[2][2] - matrix[2][1] * matrix[1][2])
                - matrix[0][1] * (constants[1] * matrix[2][2] - matrix[1][2] * constants[2])
                + matrix[0][2] * (constants[1] * matrix[2][1] - matrix[1][1] * constants[2]);

        double detY = matrix[0][0] * (constants[1] * matrix[2][2] - constants[2] * matrix[1][2])
                - constants[0] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * constants[2] - constants[1] * matrix[2][0]);;

        double detZ = matrix[0][0] * (matrix[1][1] * constants[2] - constants[1] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * constants[2] - constants[1] * matrix[2][0])
                + constants[0] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

        // Calculating coefficients
        double a = detX / detMatrix;
        double b = detY / detMatrix;
        double c = detZ / detMatrix;

        return new double[]{a, b, c};
    }

}
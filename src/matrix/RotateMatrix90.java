package matrix;

import java.util.*;

/**
 * Problem:
 * Given a matrix, rotate it 90 degrees clockwise in-place.
 *
 * Coding Platforms:
 * - LeetCode (Rotate Image):
 * https://leetcode.com/problems/rotate-image/
 * - GeeksForGeeks:
 * https://www.geeksforgeeks.org/inplace-rotate-square-matrix-by-90-degrees/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/rotate-matrix_6929312
 *
 * Notes:
 * - Usually the problem is defined for an N x N square matrix.
 * - Rotation must be in-place (no extra matrix).
 * - Two common approaches:
 * 1. Transpose + Reverse rows (Interview standard).
 * 2. Layer-by-layer rotation (four-way swap).
 */
public class RotateMatrix90 {

    public static void main(String[] args) {
        int[][][] tests = {
                { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } },
                { { 5, 1 }, { 2, 3 } },
                { { 1 } },
                { { 1, 2 }, { 3, 4 } }
        };

        int[][][] expected = {
                { { 7, 4, 1 }, { 8, 5, 2 }, { 9, 6, 3 } },
                { { 2, 5 }, { 3, 1 } },
                { { 1 } },
                { { 3, 1 }, { 4, 2 } }
        };

        for (int t = 0; t < tests.length; t++) {
            int[][] matrix = deepCopy(tests[t]);
            System.out.println("Test #" + (t + 1));
            printMatrix("Input", tests[t]);
            printMatrix("Expected", expected[t]);

            int[][] a1 = deepCopy(matrix);
            rotateBruteForce(a1);
            printMatrix("[Brute Force]", a1);

            int[][] a2 = deepCopy(matrix);
            rotateTransposeReverse(a2);
            printMatrix("[Transpose+Reverse]", a2);

            int[][] a3 = deepCopy(matrix);
            rotateLayerSwap(a3);
            printMatrix("[Layer-by-Layer]", a3);

            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force (Extra Matrix)
     * - Use an extra matrix to place rotated elements.
     * - For each (i, j), new[j][n-1-i] = old[i][j].
     * - Copy result back to original.
     * Time: O(n^2), Space: O(n^2).
     */
    public static void rotateBruteForce(int[][] matrix) {
        int n = matrix.length;
        int[][] rotated = new int[n][n];

        // Fill rotated matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }

        // Copy back to original
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rotated[i][j];
            }
        }
    }

    /**
     * Approach 2: Transpose + Reverse rows (Interview Standard)
     * - Transpose the matrix (swap rows with cols).
     * - Reverse each row.
     * Time: O(n^2), Space: O(1).
     */
    public static void rotateTransposeReverse(int[][] matrix) {
        int n = matrix.length;

        // Transpose
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Reverse each row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

    /**
     * Approach 3: Layer-by-Layer rotation (Four-way swap)
     * - Rotate groups of 4 cells at a time, moving layer by layer inward.
     * Time: O(n^2), Space: O(1).
     */
    public static void rotateLayerSwap(int[][] matrix) {
        // TODO: implement 4-way layer rotation
    }

    // ===== Helper Functions =====
    private static int[][] deepCopy(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
        return copy;
    }

    private static void printMatrix(String label, int[][] matrix) {
        System.out.println(label + ":");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
package matrix;

import java.util.*;

/**
 * Problem:
 * Given a matrix, if an element in the matrix is 0, then set its entire row
 * and column to 0. Return the modified matrix.
 *
 * Coding Platforms:
 * - LeetCode (Set Matrix Zeroes):
 * https://leetcode.com/problems/set-matrix-zeroes/
 * - GeeksForGeeks:
 * https://www.geeksforgeeks.org/a-boolean-matrix-question/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/set-matrix-zeros_3846774
 *
 * Notes:
 * - Modifications must be done in-place if possible.
 * - The straightforward way uses O(m+n) space with row/col markers.
 * - The interview-standard solution uses O(1) space with first row & col as
 * markers.
 */
public class SetMatrixZeroes {

    public static void main(String[] args) {
        int[][][] tests = {
                { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } }, // expected: [[1,0,1],[0,0,0],[1,0,1]]
                { { 0, 1, 2, 0 }, { 3, 4, 5, 2 }, { 1, 3, 1, 5 } }, // expected: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
                { { 1, 2 }, { 3, 4 } }, // no zero → unchanged
        };

        int[][][] expected = {
                { { 1, 0, 1 }, { 0, 0, 0 }, { 1, 0, 1 } },
                { { 0, 0, 0, 0 }, { 0, 4, 5, 0 }, { 0, 3, 1, 0 } },
                { { 1, 2 }, { 3, 4 } },
        };

        for (int t = 0; t < tests.length; t++) {
            int[][] matrix = deepCopy(tests[t]);
            System.out.println("Test #" + (t + 1));
            printMatrix("Input", tests[t]);
            printMatrix("Expected", expected[t]);
            matrix.clone();

            int[][] a1 = deepCopy(matrix);
            setZeroesBrute(a1);
            printMatrix("[Brute O(m*n*(m+n))]", a1);

            int[][] a2 = deepCopy(matrix);
            setZeroesBetter(a2);
            printMatrix("[Better O(m+n)]", a2);

            int[][] a3 = deepCopy(matrix);
            setZeroesOptimal(a3);
            printMatrix("[Optimal O(1)]", a3);

            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force
     * - Traverse matrix. When a zero is found, mark its row & col with a
     * placeholder (e.g., -1).
     * - In a second pass, set all placeholders to 0.
     * Time: O(m*n*(m+n)), Space: O(1) but modifies with placeholder.
     */
    public static void setZeroesBrute(int[][] matrix) {

    }

    /**
     * Approach 2: Better (Row and Col marker arrays)
     * - Use two arrays to mark which rows and cols need to be zeroed.
     * - Traverse once to mark, another pass to update.
     * Time: O(m*n), Space: O(m+n).
     */
    public static void setZeroesBetter(int[][] matrix) {
        // TODO: implement row/col marker arrays
    }

    /**
     * Approach 3: Optimal (In-place using first row & col as marker)
     * - Use first row and first col as dummy arrays to store zero info.
     * - Traverse smartly to avoid overwriting markers.
     * Time: O(m*n), Space: O(1).
     */
    public static void setZeroesOptimal(int[][] matrix) {
        // TODO: implement in-place marker approach
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

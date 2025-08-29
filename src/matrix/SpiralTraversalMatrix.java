package matrix;

import java.util.*;

/**
 * Problem:
 * Given a matrix, return the spiral order traversal of its elements.
 *
 * Coding Platforms:
 * - LeetCode (Spiral Matrix):
 * https://leetcode.com/problems/spiral-matrix/
 * - GeeksForGeeks (Spirally traversing a matrix):
 * https://www.geeksforgeeks.org/print-a-given-matrix-in-spiral-form/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/spiral-matrix_6922069
 *
 * Notes:
 * - Works for rectangular and square matrices.
 * - Start from top-left corner and move right → down → left → up.
 * - Stop when all elements are traversed.
 */
public class SpiralTraversalMatrix {

    public static void main(String[] args) {
        int[][][] tests = {
                { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }, // spiral = 1,2,3,6,9,8,7,4,5
                { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } }, // spiral = 1,2,3,4,8,12,11,10,9,5,6,7
                { { 1, 2 }, { 3, 4 } }, // spiral = 1,2,4,3
                { { 7 } }, // single element
                { { 1 }, { 2 }, { 3 }, { 4 } } // single column
        };

        int[][] expected = {
                { 1, 2, 3, 6, 9, 8, 7, 4, 5 },
                { 1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7 },
                { 1, 2, 4, 3 },
                { 7 },
                { 1, 2, 3, 4 }
        };

        for (int t = 0; t < tests.length; t++) {
            int[][] matrix = deepCopy(tests[t]);
            System.out.println("Test #" + (t + 1));
            printMatrix("Input", tests[t]);
            System.out.println("Expected: " + Arrays.toString(expected[t]));

            List<Integer> ans1 = spiralBrute(matrix);
            List<Integer> ans2 = spiralOptimal(matrix);

            System.out.println("[Brute]   -> " + ans1);
            System.out.println("[Optimal] -> " + ans2);
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force (Visited Matrix)
     * - Use a boolean visited[][] to track visited cells.
     * - Simulate moving right, down, left, up in loops until all visited.
     * Time: O(m*n), Space: O(m*n).
     */
    public static List<Integer> spiralBrute(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        List<Integer> result = new ArrayList<>();
        boolean[][] visited = new boolean[m][n];

        // Directions: right, down, left, up
        int[][] dirs = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        int dir = 0; // start with right

        int r = 0, c = 0;
        for (int k = 0; k < m * n; k++) {
            result.add(matrix[r][c]);
            visited[r][c] = true;

            // Next cell
            int nr = r + dirs[dir][0];
            int nc = c + dirs[dir][1];

            // If out of bounds or already visited, turn right
            if (nr < 0 || nr >= m || nc < 0 || nc >= n || visited[nr][nc]) {
                dir = (dir + 1) % 4; // change direction
                nr = r + dirs[dir][0];
                nc = c + dirs[dir][1];
            }

            r = nr;
            c = nc;
        }
        return result;
    }

    /**
     * Approach 2: Optimal (Boundary Pointers)
     * - Use four boundaries: top, bottom, left, right.
     * - Traverse layers by moving boundaries inward.
     * Time: O(m*n), Space: O(1) extra.
     */
    public static List<Integer> spiralOptimal(int[][] matrix) {
        int top = 0, left = 0, bottom = matrix.length - 1, right = matrix[0].length - 1;
        List<Integer> result = new ArrayList<>();
        while (top <= bottom && left <= right) {
            // Traverse from left to right
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++;
            // Traverse from top to bottom
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            if (top <= bottom) {
                // Traverse from right to left
                for (int i = right; i >= left; i--) {
                    result.add(matrix[bottom][i]);
                }
                bottom--;
            }
            if (left <= right) {
                // Traverse from bottom to top
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }
        return result;
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

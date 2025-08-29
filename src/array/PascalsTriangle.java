package array;

import java.util.*;

/**
 * Problem:
 * Pascal’s Triangle Variations:
 *
 * Variation 1:
 * - Given row number r and column number c.
 * - Print the element at position (r, c) in Pascal’s triangle.
 *
 * Variation 2:
 * - Given the row number n.
 * - Print the n-th row of Pascal’s triangle.
 *
 * Variation 3:
 * - Given the number of rows n.
 * - Print the first n rows of Pascal’s triangle.
 *
 * Coding Platforms:
 * - LeetCode (Pascal's Triangle):
 * https://leetcode.com/problems/pascals-triangle/
 * - LeetCode (Pascal's Triangle II):
 * https://leetcode.com/problems/pascals-triangle-ii/
 * - GeeksForGeeks:
 * https://www.geeksforgeeks.org/pascal-triangle/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/pascal-s-triangle_1089580
 *
 * Notes:
 * - Pascal’s Triangle is based on binomial coefficients.
 * - Formula: element at (r,c) = C(r-1, c-1) = (r-1)! / ((c-1)! * (r-c)!).
 * - Each row starts and ends with 1.
 */
public class PascalsTriangle {

    public static void main(String[] args) {
        System.out.println("=== Variation 1: Element at (r,c) ===");
        System.out.println("Input: r=5, c=3 → Expected: 6");
        System.out.println("Output: " + pascalElement(5, 3));
        System.out.println();

        System.out.println("=== Variation 2: n-th Row ===");
        System.out.println("Input: n=5 → Expected: [1, 4, 6, 4, 1]");
        System.out.println("Output: " + pascalRow(5));
        System.out.println();

        System.out.println("=== Variation 3: First n Rows ===");
        System.out.println("Input: n=5");
        List<List<Long>> rows = pascalTriangle(5);
        for (List<Long> row : rows) {
            System.out.println(row);
        }
    }

    // ===================================================
    // Variation 1: Element at (r,c) using nCr formula
    // nCr = n! / (r! * (n-r)!)
    // (n / 1)*((n-1) / 2)*.....*((n-r+1) / r).
    // ===================================================
    public static Long pascalElement(int r, int c) {
        // TODO: implement binomial coefficient formula
        return nCr(r - 1, c - 1);
    }

    /**
     * Compute nCr (binomial coefficient).
     * Formula: C(n, r) = n! / (r! * (n-r)!)
     * Optimized using iterative multiplication.
     * Iterative Formula: (n/ 1)*((n-1) / 2)*.....*((n-r+1) / r).
     * Time: O(r), Space: O(1)
     */
    public static long nCr(int n, int r) {
        if (r > n)
            return 0;
        if (r == 0 || r == n)
            return 1;

        r = Math.min(r, n - r); // because C(n, r) = C(n, n-r)
        long res = 1;

        for (int i = 0; i < r; i++) {
            res *= (n - i);
            res /= (i + 1);
        }
        return res;
    }

    // ===================================================
    // Variation 2: Print the n-th row
    // Brute Force: generate full triangle up to n rows
    // Optimal: compute row directly using nCr iterative formula
    // ===================================================
    public static List<Long> pascalRow(int n) {
        ArrayList<Long> row = new ArrayList<>();
        for (int c = 1; c <= n; c++) {
            row.add(nCr(n - 1, c - 1));
        }
        return row;
    }

    // ===================================================
    // Variation 3: Generate first n rows
    // Optimal: build row by row using previous row
    // ===================================================
    public static List<List<Long>> pascalTriangle(int n) {
        // TODO: generate first n rows
        List<List<Long>> triangle = new ArrayList<>();
        for (int r = 1; r <= n; r++) {
            triangle.add(pascalRow(r));
        }
        return triangle;
    }
}

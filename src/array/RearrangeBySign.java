package array;

import java.util.*;

/**
 * Coding Platforms:
 * - LeetCode (Rearrange Array Elements by Sign):
 * https://leetcode.com/problems/rearrange-array-elements-by-sign/
 * - GeeksForGeeks (Alternate positive/negative, stable order):
 * https://www.geeksforgeeks.org/dsa/rearrange-array-alternating-positive-negative-items-o1-extra-space/
 * - takeUforward tutorial (by-sign rearrangement):
 * https://takeuforward.org/arrays/rearrange-array-elements-by-sign/
 *
 * Problem:
 * Given array A of even length N with equal number of positive and negative
 * elements,
 * return an array where signs alternate (+, -, +, -, ...) without changing the
 * relative order
 * of positives among themselves or negatives among themselves.
 *
 * Notes:
 * - Equal counts and stable order imply a simple queue-based interleaving.
 * - Start with a positive at index 0 (standard convention on platforms).
 */
public class RearrangeBySign {

    public static void main(String[] args) {
        int[][] tests = {
                { 1, 2, -4, -5, 3, -4 }, // -> [1, -4, 2, -5, 3, -4]
                { 3, -2, 1, -7, -5, 4, -1, 2 }, // equal counts: 4 positives, 4 negatives
                { 5, -1, -2, 3, -4, 6, -7, 8 }, // mixed, already partly alternating
        };
        int[][] expected = {
                { 1, -4, 2, -5, 3, -4 },
                { 3, -2, 1, -7, 4, -5, 2, -1 }, // one valid stable arrangement (positives 3,1,4,2; negatives
                                                // -2,-7,-5,-1)
                { 5, -1, 3, -2, 6, -4, 8, -7 },
        };

        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(arr));
            System.out.println("Expected: " + Arrays.toString(expected[t]));

            int[] ansStable = solveStableAlternate(arr);
            int[] ansOneLoop = solveInOneLoop(arr);

            System.out.println("[Stable O(N) aux] -> " + Arrays.toString(ansStable));
            System.out.println("[OneLoop O(N) aux] -> " + Arrays.toString(ansOneLoop));

            System.out.println();
        }
    }

    /**
     * Stable O(N) solution using auxiliary arrays.
     *
     * Algorithm:
     * - Collect positives in order into P, negatives into N.
     * - Fill result R with P at even indices and N at odd indices.
     * Correct because:
     * - Equal counts guarantee all slots fill perfectly.
     * - Writing back in list order preserves relative order within each sign.
     *
     * Time: O(N)
     * Space: O(N)
     */
    public static int[] solveStableAlternate(int[] A) {
        List<Integer> pos = new ArrayList<>();
        List<Integer> neg = new ArrayList<>();
        for (int x : A) {
            if (x >= 0)
                pos.add(x);
            else
                neg.add(x);
        }

        int n = A.length;
        int[] res = new int[n];
        int i = 0, j = 0;
        for (int k = 0; k < n; k++) {
            if ((k & 1) == 0) {
                res[k] = pos.get(i++);
            } else {
                res[k] = neg.get(j++);
            }
        }
        return res;
    }

    /**
     * Approach 2: Stable O(N) with O(N) extra space.
     * Collect positives and negatives in order, then fill even/odd indices.
     */
    public static int[] solveInOneLoop(int[] A) {
        int n = A.length;
        int[] res = new int[n];
        int posIdx = 0; // even indices
        int negIdx = 1; // odd indices
        // First pass: place directly into res using parity pointers
        for (int x : A) {
            if (x >= 0) { // treat 0 as non-negative; adjust if needed
                res[posIdx] = x;
                posIdx += 2;
            } else {
                res[negIdx] = x;
                negIdx += 2;
            }
        }
        return res;
    }
}

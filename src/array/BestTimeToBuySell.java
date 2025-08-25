package array;

import java.util.*;

/**
 * Coding Platforms:
 * - LeetCode (Best Time to Buy and Sell Stock):
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 * - GeeksForGeeks (Max one Transaction Allowed):
 * https://www.geeksforgeeks.org/dsa/best-time-to-buy-and-sell-stock/
 * - Coding Ninjas (Best Time to Buy and Sell Stock):
 * https://www.codingninjas.com/studio/problems/maximum-profit_3189113
 * - CodeChef (Practice; search \"Best Time to Buy and Sell Stock\"):
 * https://www.codechef.com/practice
 *
 * Problem:
 * Given prices[i] = stock price on day i, choose a buy day and a later sell day
 * to maximize profit. Return max profit; if none, return 0.
 *
 * Notes:
 * - Single transaction only (one buy, one sell).
 * - If all decreasing, answer is 0.
 */
public class BestTimeToBuySell {

    public static void main(String[] args) {
        int[][] tests = {
                { 7, 1, 5, 3, 6, 4 }, // profit 5 (buy 1 sell 6)
                { 7, 6, 4, 3, 1 }, // profit 0
                { 1, 2, 3, 4, 5 }, // profit 4
                { 2, 4, 1 }, // profit 2
                { 3 }, // profit 0 (not impactful but okay to keep)
                { 2, 1, 2, 1, 0, 1, 2 }, // profit 2 (buy 0 sell 2)
        };
        int[] expected = { 5, 0, 4, 2, 0, 2 };

        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(arr));
            System.out.println("Expected: " + expected[t]);

            int a1 = maxProfitOnePass(arr); // recommended
            int a2 = maxProfitBrute(arr); // optional baseline

            System.out.println("[OnePass]     -> " + a1);
            System.out.println("[Brute O(n^2)]-> " + a2);
            System.out.println();
        }
    }

    /**
     * One-pass greedy solution.
     * Algorithm:
     * - Track minSoFar and best (max profit).
     * - For each price p: best = max(best, p - minSoFar); minSoFar = min(minSoFar,
     * p).
     * Time: O(n), Space: O(1)
     */
    public static int maxProfitOnePass(int[] prices) {
        int minSoFar = Integer.MAX_VALUE;

        int best = 0;

        for (int i = 0; i < prices.length; i++) {
            minSoFar = Math.min(minSoFar, prices[i]);
            best = Math.max(best, prices[i] - minSoFar);
        }

        return best;
    }

    /**
     * Brute force baseline: check all buy<sell pairs.
     * Time: O(n^2), Space: O(1)
     */
    public static int maxProfitBrute(int[] prices) {
        int n = prices.length;
        int best = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                best = Math.max(best, prices[j] - prices[i]);
            }
        }
        return best;
    }
}

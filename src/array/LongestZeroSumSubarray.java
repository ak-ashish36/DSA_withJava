package array;

import java.util.*;

/**
 * Problem:
 * Given an array containing both positive and negative integers,
 * find the length of the longest subarray with sum = 0.
 *
 * Coding Platforms:
 * - GeeksForGeeks:
 * https://www.geeksforgeeks.org/find-the-largest-subarray-with-0-sum/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/longest-subarray-with-zero-sum_920523
 *
 * Notes:
 * - Uses prefix sum technique.
 * - If prefix sum repeats, subarray between indices has sum = 0.
 */
public class LongestZeroSumSubarray {

    public static void main(String[] args) {
        int[][] tests = {
                { 1, -1, 3, 2, -2, -3, 3 }, // 6 -> subarray [3,2,-2,-3,3]
                { 1, 2, -3, 3, -1, 2 }, // 3 -> subarray [1,2,-3]
                { 1, 2, 3 }, // 0 -> no zero sum subarray
                { 0, 0, 0, 0 }, // 4 -> whole array
                { -1, 1 }, // 2 -> whole array
        };
        int[] expected = { 6, 3, 0, 4, 2 };

        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input: " + Arrays.toString(arr));
            System.out.println("Expected: " + expected[t]);

            int brute = longestZeroSumBrute(arr);
            int optimal = longestZeroSumOptimal(arr);

            System.out.println("[Brute O(n^2)] -> " + brute);
            System.out.println("[Optimal O(n)] -> " + optimal);
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force
     * - Check all subarrays (i..j), compute sum.
     * - If sum == 0, track length.
     * Time: O(n^2), Space: O(1).
     */
    public static int longestZeroSumBrute(int[] nums) {
        int n = nums.length;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum == 0) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }

    /**
     * Approach 2: Optimal (Prefix Sum + HashMap)
     * - Keep running prefix sum.
     * - If prefix sum seen before at index idx,
     * subarray between idx+1..current has sum = 0.
     * - Store only first occurrence for longest length.
     * Time: O(n), Space: O(n).
     */
    public static int longestZeroSumOptimal(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>(); // prefixSum -> first index
        int prefixSum = 0, maxLen = 0;

        for (int i = 0; i < n; i++) {
            prefixSum += nums[i];

            if (prefixSum == 0) {
                maxLen = i + 1; // from start to i
            }

            if (map.containsKey(prefixSum)) {
                maxLen = Math.max(maxLen, i - map.get(prefixSum));
            } else {
                map.put(prefixSum, i);
            }
        }
        return maxLen;
    }
}

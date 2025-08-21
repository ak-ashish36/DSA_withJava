package array;

import java.util.*;

/**
 * Problem: Given an array and a sum k, print the length of the longest subarray
 * that sums to k.
 *
 * Links to Problem:
 * - GFG: https://www.geeksforgeeks.org/longest-sub-array-sum-k/
 * - LeetCode (Variant #325):
 * https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/longest-subarray-with-sum-k_5713505
 */
public class LongestSubarraySumK {

    public static void main(String[] args) {
        // Test cases
        int[][] testCases = {
                { 10, 5, 2, 7, 1, 9 }, // Expected length = 4 (subarray [5,2,7,1] sum = 15)
                { -1, 2, 3 }, // Expected length = 2 (subarray [2,3] sum = 5)
                { 1, -1, 5, -2, 3 }, // Expected length = 4 (subarray [1,-1,5,-2] sum = 3)
                { -2, -1, 2, 1 }, // Expected length = 2 (subarray [-1,2] sum = 1)
                { 1, 2, 3 }, // Expected length = 2 (subarray [2,3] sum = 5)
                { 1, 2, 3 }, // Edge: if k=3, Expected length = 2 ([1,2])
                {} // Edge: empty array → length 0
        };
        int[] kValues = { 15, 5, 3, 1, 5, 3, 3 };
        int[] expected = { 4, 2, 4, 2, 2, 2, 0 };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input: " + Arrays.toString(testCases[i]) + ", k = " + kValues[i]);
            System.out.println("Expected Output: " + expected[i]);
            System.out.println("Brute Force: " + longestSubarrayBruteForce(testCases[i], kValues[i]));
            System.out.println("HashMap Prefix Sum: " + longestSubarrayHashing(testCases[i], kValues[i]));
            System.out.println("Sliding Window (positive numbers only): "
                    + longestSubarraySlidingWindow(testCases[i], kValues[i]));
            System.out.println("-----");
        }
    }

    /**
     * Approach 1: Brute Force (Check All Subarrays)
     * ----------------------------------------------
     * Idea:
     * - Try every subarray and calculate sum.
     * - Track max length where sum == k.
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     *
     * Not optimal, but interviewers may ask to build intuition.
     */
    public static int longestSubarrayBruteForce(int[] arr, int k) {
        int len = 0;

        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                if (sum == k) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }

        return len;
    }

    /**
     * Approach 2: Prefix Sum + HashMap
     * ---------------------------------
     * Idea:
     * - Keep running sum while iterating.
     * - If (sum - k) is found in map, update max length.
     * - Store first occurrence of each prefix sum in map.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     *
     * Best and most commonly asked approach.
     */
    public static int longestSubarrayHashing(int[] arr, int k) {
        // TODO: Implement
        Map<Integer, Integer> prefixSumMap = new HashMap<>();
        int sum = 0;
        int maxLength = 0;
        for (int j = 0; j < arr.length; j++) {
            sum += arr[j];
            if (sum == k) {
                maxLength = Math.max(maxLength, j + 1);
            }
            int x = sum - k;
            if (prefixSumMap.containsKey(x)) { //
                int i = prefixSumMap.get(x);
                maxLength = Math.max(maxLength, j - i);
            } else {
                prefixSumMap.put(sum, j);
            }
        }

        return maxLength;
    }

    /**
     * Approach 3: Sliding Window (only for positive numbers)
     * -------------------------------------------------------
     * Idea:
     * - Maintain a window [left…right] with current sum.
     * - Expand right pointer until sum >= k.
     * - Shrink from left if sum > k.
     * - If sum == k, update max length.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * Note: Works only when array has all positive numbers (because negatives break
     * the shrinking logic).
     */
    public static int longestSubarraySlidingWindow(int[] arr, int k) {
        // TODO: Implement
        int maxLen = 0;
        int left = 0, right = 0;
        int sum = 0;

        while (right < arr.length) {
            sum += arr[right];

            while (sum > k && left <= right) {
                sum -= arr[left++];
            }

            if (sum == k) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
            right++;

        }

        return maxLen;
    }
}

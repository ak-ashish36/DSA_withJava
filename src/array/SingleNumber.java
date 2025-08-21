package array;

import java.util.*;

/**
 * Problem: Given a non-empty array of integers, every element appears twice
 * except for one.
 * Find that single one.
 *
 * LeetCode Link: https://leetcode.com/problems/single-number/
 */
public class SingleNumber {

    public static void main(String[] args) {
        // Test cases
        int[][] testCases = {
                { 2, 2, 1 }, // Expected = 1
                { 4, 1, 2, 1, 2 }, // Expected = 4
                { 1 }, // Expected = 1 (edge case)
                { 7, 3, 5, 3, 7 }, // Expected = 5
                { -1, -1, -2 } // Expected = -2 (negative numbers)
        };
        int[] expected = { 1, 4, 1, 5, -2 };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input: " + Arrays.toString(testCases[i]));
            System.out.println("Expected Output: " + expected[i]);
            System.out.println("Using HashMap: " + findSingleNumberUsingMap(testCases[i]));
            System.out.println("Using HashSet: " + findSingleNumberUsingSet(testCases[i]));
            System.out.println("Using XOR: " + findSingleNumberUsingXOR(testCases[i]));
            System.out.println("-----");
        }
    }

    /**
     * Approach 1: HashMap Counting
     * -----------------------------
     * Idea:
     * - Count frequency of each element.
     * - Return the one with frequency = 1.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int findSingleNumberUsingMap(int[] arr) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : arr) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        return -1;
    }

    /**
     * Approach 2: HashSet (Sum Trick)
     * --------------------------------
     * Idea:
     * - Use the formula: 2 * (sum of unique elements) - (sum of all elements)
     * - Only the single element remains.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int findSingleNumberUsingSet(int[] arr) {
        // TODO: Implement

        Set<Integer> uniqueSet = new HashSet<>();
        int sumUnique = 0;
        int sumAll = 0;
        for (int num : arr) {
            if (!uniqueSet.contains(num)) {
                uniqueSet.add(num);
                sumUnique += num;
            }
            sumAll += num;
        }
        return 2 * sumUnique - sumAll;

    }

    /**
     * Approach 3: Bit Manipulation (XOR)
     * -----------------------------------
     * Idea:
     * - XOR of two same numbers = 0
     * - XOR of number with 0 = number
     * - XORing all numbers leaves only the single one.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int findSingleNumberUsingXOR(int[] arr) {
        int result = 0;
        for (int num : arr) {
            if (num == 0)
                continue; // Skip zeroes
            result ^= num;
        }
        return result;
    }
}

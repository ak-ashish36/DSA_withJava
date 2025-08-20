package array;

import java.util.*;

public class MaxConsecutiveOnes {

    public static void main(String[] args) {
        // Test cases with expected outputs
        int[][] testCases = {
                { 1, 1, 0, 1, 1, 1 }, // Expected = 3
                { 1, 0, 1, 1, 0, 1 }, // Expected = 2
                { 0, 0, 0, 0 }, // Expected = 0
                { 1, 1, 1, 1 }, // Expected = 4
                {} // Expected = 0
        };
        int[] expected = { 3, 2, 0, 4, 0 };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test case " + (i + 1));
            System.out.println("Input: " + Arrays.toString(testCases[i]));
            System.out.println("Expected Output: " + expected[i]);
            System.out.println("Max Consecutive Ones (Linear Scan): " + findMaxConsecutiveOnesLinear(testCases[i]));
            System.out.println("Max Consecutive Ones (Sliding Window): " + findMaxConsecutiveOnesSliding(testCases[i]));
            System.out.println("-----");
        }
    }

    /**
     * Approach 1: Linear Scan (Counter Method)
     * -----------------------------------------
     * Idea:
     * - Traverse array, keep a counter of consecutive 1s.
     * - Reset counter when 0 is found.
     * - Track maximum length so far.
     *
     * LeetCode Link: https://leetcode.com/problems/max-consecutive-ones/
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int findMaxConsecutiveOnesLinear(int[] arr) {
        int maxCount = 0, currentCount = 0;

        for (int num : arr) {
            if (num == 1) {
                currentCount++;
                maxCount = Math.max(maxCount, currentCount);
            } else {
                currentCount = 0; // Reset on encountering 0
            }
        }

        return maxCount;
    }

    /**
     * Approach 2: Sliding Window
     * ---------------------------
     * Idea:
     * - Treat as window of continuous 1s.
     * - When 0 is encountered, reset the window start.
     *
     * LeetCode Link: https://leetcode.com/problems/max-consecutive-ones/
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int findMaxConsecutiveOnesSliding(int[] arr) {

        int maxCount = 0, start = 0;

        for (int end = 0; end < arr.length; end++) {
            if (arr[end] == 0) {
                start = end + 1; // reset start after zero
            }
            maxCount = Math.max(maxCount, end - start + 1);
        }
        return maxCount;

    }
}

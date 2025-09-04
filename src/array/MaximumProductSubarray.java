package array;

import java.util.*;

/**
 * Problem:
 * Given an integer array nums (may contain both positive and negative numbers),
 * find the contiguous subarray (containing at least one number)
 * which has the largest product. Return the product.
 * 
 * Coding Platforms:
 * - LeetCode (Maximum Product Subarray):
 * https://leetcode.com/problems/maximum-product-subarray/
 * - GeeksForGeeks (Maximum Product Subarray):
 * https://www.geeksforgeeks.org/maximum-product-subarray/
 * - Coding Ninjas (Maximum Product Subarray):
 * https://www.codingninjas.com/studio/problems/maximum-product-subarray_1115474
 * - CodeChef (Practice; search "Maximum Product Subarray"):
 * https://www.codechef.com/practice
 *
 * Notes:
 * - Trickier than Maximum Sum Subarray (Kadane).
 * - Negative numbers flip the sign, so must track both
 * max and min products at every index.
 * - Zeros break subarrays, so reset when necessary.
 */
public class MaximumProductSubarray {

    public static void main(String[] args) {
        int[][] tests = {
                { 2, 3, -2, 4 }, // Expected: 6 (subarray [2,3])
                { -2, 0, -1 }, // Expected: 0 (subarray [0])
                { -2, 3, -4 }, // Expected: 24 (subarray [-2,3,-4])
                { 0, 2 }, // Expected: 2 (subarray [2])
                { -1, -3, -10, 0, 60 }, // Expected: 60 (subarray [60])
                { -2, -3, 0, -2, -40 } // Expected: 80 (subarray [-2,-40])
        };
        int[] expected = { 6, 0, 24, 2, 60, 80 };

        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(arr));
            System.out.println("Expected: " + expected[t]);

            int a1 = maxProductBrute(arr);
            int a2 = maxProductDP(arr);
            int a3 = maxProductOptimized(arr);

            System.out.println("[Brute O(n^2)]    -> " + a1);
            System.out.println("[DP O(n) space O(n)] -> " + a2);
            System.out.println("[Optimized O(n) space O(1)] -> " + a3);
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force
     * Algorithm:
     * - Consider all possible subarrays.
     * - Compute their product and track maximum.
     * 
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    public static int maxProductBrute(int[] nums) {
        int maxProduct = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int product = nums[i];
            maxProduct = Math.max(maxProduct, product);
            for (int j = i + 1; j < nums.length; j++) {
                product *= nums[j];
                maxProduct = Math.max(maxProduct, product);
            }
        }
        return maxProduct;
    }

    /**
     * Approach 2: Dynamic Programming with arrays
     * Algorithm:
     * - Maintain dpMax[i] and dpMin[i] representing max/min product ending at i.
     * - dpMax[i] = max(nums[i], nums[i]*dpMax[i-1], nums[i]*dpMin[i-1])
     * - dpMin[i] = min(nums[i], nums[i]*dpMax[i-1], nums[i]*dpMin[i-1])
     * - Answer = max(dpMax[i]).
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int maxProductDP(int[] nums) {
        int n = nums.length;
        int[] dpMax = new int[n];
        int[] dpMin = new int[n];
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];
        int result = dpMax[0];
        for (int i = 1; i < n; i++) {
            dpMax[i] = Math.max(nums[i], Math.max(nums[i] * dpMax[i - 1], nums[i] * dpMin[i - 1]));
            dpMin[i] = Math.min(nums[i], Math.min(nums[i] * dpMax[i - 1], nums[i] * dpMin[i - 1]));
            result = Math.max(result, dpMax[i]);
        }

        return result;
    }

    /**
     * Approach 3: Optimized Dynamic Programming
     * Algorithm:
     * - Instead of arrays, use only two variables: maxSoFar, minSoFar.
     * - Swap when encountering a negative number.
     * - Update result at each step.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int maxProductOptimized(int[] nums) {
        int n = nums.length;
        int maxSoFar = nums[0];
        int minSoFar = nums[0];
        int result = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] < 0) {
                int temp = maxSoFar;
                maxSoFar = minSoFar;
                minSoFar = temp;
            }
            maxSoFar = Math.max(nums[i], nums[i] * maxSoFar);
            minSoFar = Math.min(nums[i], nums[i] * minSoFar);
            result = Math.max(result, maxSoFar);
        }
        return result;

    }

    /**
     * Approach 4: Kadane-style Simplified Logic
     * Algorithm:
     * - Maintain running max and min product as we iterate.
     * - Reset to nums[i] if extending subarray reduces the value.
     * - Essentially the same as optimized DP but written in "Kadane" style:
     * maxProd = max(nums[i], maxProd * nums[i], minProd * nums[i])
     * minProd = min(nums[i], maxProd * nums[i], minProd * nums[i])
     * - Update global result at each step.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int maxProductKadaneStyle(int[] nums) {
        int n = nums.length;
        int maxProd = nums[0];
        int minProd = nums[0];
        int result = nums[0];
        for (int i = 1; i < n; i++) {
            int tempMax = maxProd; // save old max
            maxProd = Math.max(nums[i], Math.max(nums[i] * maxProd, nums[i] * minProd));
            minProd = Math.min(nums[i], Math.min(nums[i] * tempMax, nums[i] * minProd));
            result = Math.max(result, maxProd);
        }
        return result;
    }
}

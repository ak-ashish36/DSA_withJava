package array;

import java.util.*;

/**
 * Coding Platforms (official problem pages):
 * - LeetCode (Maximum Subarray):
 * https://leetcode.com/problems/maximum-subarray/
 * - GeeksForGeeks (Kadane's Algorithm):
 * https://www.geeksforgeeks.org/problems/kadanes-algorithm-1587115620/1
 * - Coding Ninjas (Maximum Subarray Sum):
 * https://www.codingninjas.com/studio/problems/maximum-subarray-sum_630526
 * - CodeChef (Practice; search "maximum subarray" / "Kadane"):
 * https://www.codechef.com/practice
 *
 * Problem:
 * Given an integer array arr, find the contiguous subarray (containing at least
 * one number)
 * which has the largest sum and return its sum.
 *
 * Notes:
 * - If the array can contain all negative numbers, the answer is the maximum
 * (least negative) element.
 * - Kadane’s algorithm achieves O(n) time and O(1) space.
 * - Divide & Conquer is a classic alternative with O(n log n).
 */
public class MaxSubarray {

    // =========================
    // Main at TOP with Impactful Test Cases
    // =========================
    public static void main(String[] args) {
        int[][] tests = {
                { 1 }, // single positive
                { -1 }, // single negative
                { -2, -3, -1, -5 }, // all negative -> max single
                { 5, 4, -1, 7, 8 }, // all positive with small negative
                { -2, 1, -3, 4, -1, 2, 1, -5, 4 }, // classic -> 6 (subarray [4,-1,2,1])
                { 1, 2, 3, -2, 5 }, // -> 9 ([1,2,3,-2,5])
                { 4, -1, 2, 1 }, // -> 6 ([4,-1,2,1])
                { -1, -2, 3, 4, -5, 8 }, // -> 10 ([3,4,-5,8] or [3,4] vs [8])
                { 100, -90, 50, -60, 70 }, // -> 100 or 120? best is 100 alone vs 100-90+50-60+70 = 70; also
                                           // 50-60+70=60; so 100
        };

        int[] expected = {
                1,
                -1,
                -1,
                23,
                6,
                9,
                6,
                10,
                100
        };

        System.out.println("==== Maximum Subarray Sum ====");
        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(arr));
            System.out.println("Expected: " + expected[t]);

            System.out.println("[Brute O(n^2)]        -> " + maxSubarrayBrute(arr));
            System.out.println("[Prefix O(n^2)]       -> " + maxSubarrayPrefix(arr));
            System.out.println("[Kadane O(n)]         -> " + maxSubarrayKadane(arr));
            System.out.println("[Divide&Conquer nlogn]-> " + maxSubarrayDivideConquer(arr));
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force
     *
     * Algorithm:
     * - For each start index i, expand end j and keep a running sum or recompute
     * sum each time.
     * - Track the maximum sum encountered.
     *
     * Time Complexity: O(n^2) with running sum; O(n^3) if summing subarrays naïvely
     * each time
     * Space Complexity: O(1)
     *
     * @param arr input array
     * @return maximum subarray sum
     */
    public static int maxSubarrayBrute(int[] arr) {
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            sum = arr[i];
            maxSum = Math.max(maxSum, sum);

            for (int j = i + 1; j < arr.length; j++) {
                sum += arr[j];
                maxSum = Math.max(maxSum, sum);
            }
        }
        return maxSum;
    }

    /**
     * Approach 2: Prefix Sums
     *
     * Algorithm:
     * - Build prefix sums P where P[0]=0 and P[i]=arr[0]+...+arr[i-1].
     * - Any subarray sum arr[l..r] = P[r+1] - P[l].
     * - Enumerate all l <= r and compute in O(1) using prefixes.
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(n)
     *
     * @param arr input array
     * @return maximum subarray sum
     */
    public static int maxSubarrayPrefix(int[] arr) {
        int[] p = new int[arr.length + 1];
        p[0] = 0;
        for (int i = 1; i < p.length; i++) {
            p[i] = p[i - 1] + arr[i - 1];
        }

        int maxSum = Integer.MIN_VALUE;
        for (int l = 0; l < arr.length; l++) {
            for (int r = l; r < arr.length; r++) {
                int subarraySum = p[r + 1] - p[l];
                maxSum = Math.max(maxSum, subarraySum);
            }
        }

        return maxSum;
    }

    /**
     * Approach 3: Kadane’s Algorithm (one-pass)
     *
     * Algorithm:
     * - Maintain currentSum and best.
     * - For each x in arr:
     * - currentSum = max(x, currentSum + x)
     * - best = max(best, currentSum)
     * - Handles all-negative arrays by initializing with first element.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param arr input array
     * @return maximum subarray sum
     */
    public static int maxSubarrayKadane(int[] arr) {
        int currsum = arr[0];
        int maxsum = arr[0];
        int startIndex = 0; // To track the start index of the maximum subarray
        int endIndex = 0; // To track the end index of the maximum subarray
        for (int i = 1; i < arr.length; i++) {
            // currsum = Math.max(arr[i], currsum + arr[i]);
            if (arr[i] > currsum + arr[i]) {
                currsum = arr[i];
                startIndex = i; // Update start index when starting a new subarray
            } else {
                currsum += arr[i];
            }

            // maxsum = Math.max(maxsum, currsum);
            if (currsum > maxsum) {
                maxsum = currsum;
                endIndex = i; // Update end index when we find a new maximum
            }
        }
        System.out.print("Kadane's Max Subarray: [");
        for (int i = startIndex; i <= endIndex; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print("]");
        System.out.println();
        return maxsum;

    }

    /**
     * Approach 4: Divide & Conquer
     *
     * Algorithm:
     * - Recursively split array into left and right halves.
     * - Answer is max of:
     * - left max subarray
     * - right max subarray
     * - max subarray crossing the midpoint (best suffix on left + best prefix on
     * right)
     *
     * Time Complexity: O(n log n)
     * Space Complexity: O(log n) recursion
     *
     * @param arr input array
     * @return maximum subarray sum
     */
    public static int maxSubarrayDivideConquer(int[] arr) {
        // TODO: implement divide & conquer helper

        return solve(arr, 0, arr.length - 1);

    }

    private static int solve(int[] arr, int l, int r) {
        if (l == r)
            return arr[l];
        int m = l + ((r - l) >>> 1);

        int leftBest = solve(arr, l, m);
        int rightBest = solve(arr, m + 1, r);
        int crossBest = maxCrossingSum(arr, l, m, r);

        return Math.max(Math.max(leftBest, rightBest), crossBest);
    }

    /**
     * Helper to compute max crossing sum around mid.
     * Time: O(r - l + 1)
     */
    @SuppressWarnings("unused")
    private static int maxCrossingSum(int[] arr, int l, int m, int r) {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = m; i >= l; i--) {
            sum += arr[i];
            leftSum = Math.max(leftSum, sum);
        }

        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int i = m + 1; i <= r; i++) {
            sum += arr[i];
            rightSum = Math.max(rightSum, sum);
        }

        return leftSum + rightSum;
    }
}

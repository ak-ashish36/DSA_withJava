package array;

import java.util.*;

/**
 * Problem:
 * Given an array of integers and an integer k, return the total number of
 * subarrays whose sum equals k.
 *
 * Coding Platforms:
 * - LeetCode (Subarray Sum Equals K):
 * https://leetcode.com/problems/subarray-sum-equals-k/
 * - GeeksForGeeks:
 * https://www.geeksforgeeks.org/number-subarrays-sum-exactly-equal-k/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/subarray-sum-equals-k_691263
 *
 * Notes:
 * - Subarray must be contiguous.
 * - Negative numbers allowed.
 * - Brute force is O(n^2) using prefix sums.
 * - Interview standard is O(n) using HashMap (prefix sum + frequency).
 */
public class SubarraySumEqualsK {

    public static void main(String[] args) {
        int[][] tests = {
                { 1, 1, 1 }, // k = 2 → 2 subarrays
                { 1, 2, 3 }, // k = 3 → 2 subarrays ([1,2], [3])
                { 3, 4, 7, 2, -3, 1, 4, 2 }, // k = 7 → multiple
                { 1, -1, 0 }, // k = 0 → 3 subarrays
                {}, // k = 0 → 0
                { 5 }, // k = 5 → 1
        };
        int[] ks = { 2, 3, 7, 0, 0, 5 };
        int[] expected = { 2, 2, 4, 3, 0, 1 };

        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(arr) + ", k=" + ks[t]);
            System.out.println("Expected: " + expected[t]);

            int a1 = subarraySumBrute(arr, ks[t]);
            int a2 = subarraySumHashMap(arr, ks[t]); // interview approach

            System.out.println("[Brute O(n^2)]   -> " + a1);
            System.out.println("[HashMap O(n)]   -> " + a2);
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute force (prefix sum in O(n^2))
     * - Compute sum of every subarray and count if equal to k.
     * Time: O(n^2), Space: O(1).
     */
    public static int subarraySumBrute(int[] nums, int k) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {

            int sum = nums[i];
            if (sum == k) {
                count++;
            }
            for (int j = i + 1; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    count++;
                }

            }
        }
        return count;

    }

    /**
     * Approach 2: HashMap + Prefix Sum (Interview Standard)
     * Algorithm:
     * - Maintain prefix sum while traversing.
     * - For each prefix sum "currSum", check if (currSum - k) exists in map.
     * If yes, add its frequency to count.
     * - Store frequency of prefix sums in map.
     *
     * Time: O(n), Space: O(n).
     */
    public static int subarraySumHashMap(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        int count=0;
        for(int i=0;i<nums.length;i++){
            int sum=nums[i];
            if(sum==k){
                count++;
            }
            int x = sum-k;
            if(map.containsKey(x)){
                count += map.get(x);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}

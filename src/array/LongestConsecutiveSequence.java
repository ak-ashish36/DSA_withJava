package array;

import java.util.*;

/**
 * Problem:
 * You are given an array of N integers. You need to find the length of the
 * longest sequence which contains consecutive elements.
 *
 * Coding Platforms:
 * - LeetCode (Longest Consecutive Sequence):
 * https://leetcode.com/problems/longest-consecutive-sequence/
 * - GeeksForGeeks:
 * https://www.geeksforgeeks.org/longest-consecutive-subsequence/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/longest-consecutive-sequence_759408
 *
 * Notes:
 * - Sequence elements do not need to be contiguous in array, only consecutive
 * in value.
 * - Duplicates should not affect sequence length.
 * - Empty array → answer = 0.
 */
public class LongestConsecutiveSequence {

    public static void main(String[] args) {
        int[][] tests = {
                { 100, 4, 200, 1, 3, 2 }, // longest = 4 (1,2,3,4)
                { 0, 3, 7, 2, 5, 8, 4, 6, 0, 1 }, // longest = 9 (0-8)
                { 9, 1, 4, 7, 3, -1, 0, 5, 8, -1, 6 }, // longest = 7 (-1,0,1,3...??) -> actually (0-9 without 2) → 7
                { 1, 2, 0, 1 }, // longest = 3 (0,1,2)
                { 10 }, // longest = 1
                {} // longest = 0
        };

        int[] expected = { 4, 9, 7, 3, 1, 0 };

        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(arr));
            System.out.println("Expected: " + expected[t]);

            int a1 = longestConsecutiveBrute(arr);
            int a2 = longestConsecutiveSorting(arr.clone());
            int a3 = longestConsecutiveHashSet(arr); // interview standard

            System.out.println("[Brute O(n^2)]  -> " + a1);
            System.out.println("[Sorting O(n log n)]  -> " + a2);
            System.out.println("[HashSet O(n)]  -> " + a3);
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute force
     * For each element, check if next consecutive exists by scanning array.
     * Time: O(n^2), Space: O(1)
     */
    public static int longestConsecutiveBrute(int[] a) {
        int n = a.length;
        if (n == 0) {
            return 0;
        }
        int max = 1;
        for (int i = 0; i < n; i++) {
            int curr = a[i];
            int count = 1;
            while (exists(a, curr + 1)) {
                count++;
                curr++;
            }
            max = Math.max(max, count);
        }
        return max;

    }

    /**
     * Approach 2: Sorting (Optimal)
     * Algorithm:
     * - Sort the array.
     * - Iterate through the sorted array and count consecutive elements.
     * - Track maximum length.
     *
     * Time: O(n), Space: O(n)
     */
    public static int longestConsecutiveSorting(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        Arrays.sort(arr);
        int max = 1;
        int curr = arr[0];
        int count = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == curr + 1) {
                count++;
                max = Math.max(max, count);
            } else if (arr[i] == curr) {
                continue;
            } else {
                count = 1;
            }
            curr = arr[i];

        }
        return max;
    }

    /**
     * Approach 3: HashSet (Interview Standard)
     * Algorithm:
     * - Put all numbers in a HashSet.
     * - For each number, if it's the start of a sequence (num-1 not in set),
     * expand forward until sequence ends.
     * - Track maximum length.
     *
     * Time: O(n), Space: O(n)
     */
    public static int longestConsecutiveHashSet(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        Set<Integer> set = new HashSet<Integer>();
        for (int x : arr) {
            set.add(x);
        }
        int max = 1;
        for (int x : set) {
            if (!set.contains(x - 1)) {
                int curr = x;
                int count = 1;
                while (set.contains(curr + 1)) {
                    count++;
                    curr++;
                }
                max = Math.max(max, count);
            }
        }
        return max;
    }

    /**
     * Helpers
     */
    public static boolean exists(int[] a, int num) {
        int n = a.length; // size of array
        for (int i = 0; i < n; i++) {
            if (a[i] == num)
                return true;
        }
        return false;
    }
}

package array;

import java.util.*;

/**
 * Coding Platforms (official problem pages):
 * - LeetCode (Sort Colors): https://leetcode.com/problems/sort-colors/
 * - GeeksForGeeks (Sort an array of 0s, 1s and 2s):
 * https://www.geeksforgeeks.org/problems/sort-an-array-of-0s-1s-and-2s4231/1
 * - Coding Ninjas (Sort 0 1 2):
 * https://www.codingninjas.com/studio/problems/sort-0-1-2_631055
 * - CodeChef (Practice; search "Sort Colors" or "0 1 2 sort"):
 * https://www.codechef.com/practice
 *
 * Problem:
 * Given an array consisting of only 0s, 1s, and 2s. Sort the array in-place
 * without using built-in sort.
 *
 * Approaches included:
 * - Counting (two/three-pass)
 * - Dutch National Flag (one-pass, in-place)
 * - Two-pass partitioning with pointers
 *
 * Notes:
 * - Aim for O(n) time and O(1) extra space.
 * - Edge cases: all same value, already sorted, reverse sorted, mixed.
 */
public class SortColors {

    // =========================
    // Main at TOP with Impactful Test Cases Only
    // =========================
    public static void main(String[] args) {
        // Removed empty {} and single-element {0}, {1}, {2} tests from execution to
        // focus on impactful cases.
        int[][] tests = {
                { 0, 1, 2 }, // already sorted (minimal impact but still shows identity)
                { 2, 1, 0 }, // reverse sorted
                { 2, 0, 2, 1, 1, 0 }, // mixed classic
                { 0, 0, 0 }, // all zeros
                { 1, 1, 1 }, // all ones
                { 2, 2, 2 }, // all twos
                { 1, 2, 0, 1, 2, 0 }, // repeated pattern
        };

        int[][] expected = {
                { 0, 1, 2 },
                { 0, 1, 2 },
                { 0, 0, 1, 1, 2, 2 },
                { 0, 0, 0 },
                { 1, 1, 1 },
                { 2, 2, 2 },
                { 0, 0, 1, 1, 2, 2 },
        };

        for (int t = 0; t < tests.length; t++) {
            System.out.println("==== Test #" + (t + 1) + " ====");
            int[] arr1 = Arrays.copyOf(tests[t], tests[t].length);
            int[] arr2 = Arrays.copyOf(tests[t], tests[t].length);
            int[] arr3 = Arrays.copyOf(tests[t], tests[t].length);

            System.out.println("Input:     " + Arrays.toString(tests[t]));
            System.out.println("Expected:  " + Arrays.toString(expected[t]));

            sortCount(arr1);
            sortDutchFlag(arr2);
            sortTwoPassPointers(arr3);

            System.out.println("[Count]           -> " + Arrays.toString(arr1));
            System.out.println("[DutchNational]   -> " + Arrays.toString(arr2));
            System.out.println("[TwoPassPointers] -> " + Arrays.toString(arr3));
            System.out.println();
        }
    }

    // =========================
    // Approach 1: Counting (Two/Three-pass)
    // =========================

    /**
     * Approach 1: Counting sort for 0/1/2
     *
     * Algorithm:
     * - Count number of 0s, 1s, 2s in one pass.
     * - Overwrite the array with that many 0s, then 1s, then 2s.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * Stability: Not required here since values are only 0/1/2.
     *
     * @param arr array of 0s,1s,2s to be sorted in-place
     */
    public static void sortCount(int[] arr) {
        int zeros = 0, ones = 0, twos = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0)
                zeros++;
            else if (arr[i] == 1)
                ones++;
            else if (arr[i] == 2)
                twos++;
        }
        for (int i = 0; i < arr.length; i++) {
            if (i < zeros)
                arr[i] = 0;
            else if (i < zeros + ones)
                arr[i] = 1;
            else
                arr[i] = 2;
        }
    }

    // =========================
    // Approach 2: Dutch National Flag (One-pass, in-place)
    // =========================

    /**
     * Approach 2: Dutch National Flag (one-pass, constant space)
     *
     * Invariant:
     * - [0..low-1] -> 0
     * - [low..mid-1] -> 1
     * - [mid..high] -> unknown
     * - [high+1..n-1] -> 2
     *
     * Algorithm:
     * - Initialize low=0, mid=0, high=n-1.
     * - While mid <= high:
     * - if arr[mid]==0: swap(arr[low], arr[mid]); low++; mid++;
     * - else if arr[mid]==1: mid++;
     * - else (arr[mid]==2): swap(arr[mid], arr[high]); high-- (mid stays)
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param arr array of 0s,1s,2s to be sorted in-place
     */
    public static void sortDutchFlag(int[] arr) {
        int low = 0, mid = 0, high = arr.length - 1;
        while (mid <= high) {
            if (arr[mid] == 0) {
                swap(arr, mid++, low++);
            } else if (arr[mid] == 1) {
                mid++;
            } else {
                swap(arr, mid, high--);
            }
        }
    }

    // =========================
    // Approach 3: Two-pass Pointers (Partition style)
    // =========================

    /**
     * Approach 3: Two-pass partitioning with pointers
     *
     * Idea:
     * - First pass: move all 0s to front by swapping with a running left pointer.
     * - Second pass: from the position after last 0, move all 1s forward (2s
     * naturally go to the end).
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param arr array of 0s,1s,2s to be sorted in-place
     */
    public static void sortTwoPassPointers(int[] arr) {
        int left = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                swap(arr, i, left++);
            }
        }
        int mid = left;
        for (int i = left; i < arr.length; i++) {
            if (arr[i] == 1) {
                swap(arr, i, mid++);
            }
        }
    }

    // =========================
    // Helpers
    // =========================

    /**
     * Swap elements at i and j in arr.
     * Time: O(1), Space: O(1)
     */
    private static void swap(int[] arr, int i, int j) {
        // Optional helper if you choose to use it in your implementations
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

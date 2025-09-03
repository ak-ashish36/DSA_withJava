package array;

import java.util.*;

/**
 * Problem:
 * Given an array of N integers, count the total number of inversions.
 * An inversion is a pair (i, j) such that i < j and arr[i] > arr[j].
 *
 * Coding Platforms:
 * - LeetCode (Reverse Pairs variant):
 * https://leetcode.com/problems/reverse-pairs/
 * - GeeksForGeeks (Count Inversions):
 * https://www.geeksforgeeks.org/counting-inversions/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/count-inversions_615
 *
 * Notes:
 * - Brute Force: Check all pairs (i, j).
 * - Optimal: Modified Merge Sort counts inversions during merging.
 */
public class CountInversions {

    public static void main(String[] args) {
        int[][] tests = {
                { 2, 4, 1, 3, 5 }, // 3 inversions -> (2,1), (4,1), (4,3)
                { 5, 4, 3, 2, 1 }, // 10 (worst case, fully descending)
                { 1, 2, 3, 4, 5 }, // 0 (already sorted)
                { 1 }, // 0 (single element)
                { 1, 20, 6, 4, 5 }, // 5 inversions
                { 1, 3, 2, 3, 1 } // 4 inversions
        };
        int[] expected = { 3, 10, 0, 0, 5, 4 };

        for (int t = 0; t < tests.length; t++) {
            int[] arr1 = Arrays.copyOf(tests[t], tests[t].length);
            int[] arr2 = Arrays.copyOf(tests[t], tests[t].length);

            System.out.println("Test #" + (t + 1));
            System.out.println("Input: " + Arrays.toString(tests[t]));
            System.out.println("Expected: " + expected[t]);

            long brute = countInversionsBrute(arr1);
            long optimal = countInversionsMergeSort(arr2);

            System.out.println("[Brute O(n^2)] -> " + brute);
            System.out.println("[Optimal O(n log n)] -> " + optimal);
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force
     * - Check all pairs (i, j) where i < j.
     * - Count if arr[i] > arr[j].
     * Time: O(n^2), Space: O(1).
     */
    public static long countInversionsBrute(int[] arr) {
        long count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Approach 2: Merge Sort Based
     * - Modify merge sort to count cross-inversions.
     * - During merge step:
     * If left[i] > right[j], then all elements from left[i..end] are greater than
     * right[j].
     *
     * Time: O(n log n)
     * Space: O(n)
     */
    public static long countInversionsMergeSort(int[] arr) {
        return mergeSort(arr, 0, arr.length - 1);
    }

    private static long mergeSort(int[] arr, int l, int r) {
        long count = 0;
        if (l < r) {
            int mid = l + (r - l) / 2;
            count += mergeSort(arr, l, mid);
            count += mergeSort(arr, mid + 1, r);
            count += merge(arr, l, mid, r);
        }
        return count;
    }

    private static long merge(int[] arr, int l, int mid, int r) {
        int n1 = mid - l + 1;
        int n2 = r - mid;

        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; i++)
            left[i] = arr[l + i];
        for (int j = 0; j < n2; j++)
            right[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = l;
        long count = 0;

        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
                count += (n1 - i); // all remaining left[i..end] form inversions
                // Since both left[] and right[] are already sorted, not only left[i] but ALL
                // elements from left[i] to left[n1-1] will also be greater than right[j].
            }
        }

        while (i < n1)
            arr[k++] = left[i++];
        while (j < n2)
            arr[k++] = right[j++];

        return count;
    }
}

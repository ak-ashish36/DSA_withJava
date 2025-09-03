package array;

import java.util.*;

/**
 * Problem:
 * Count reverse pairs in an array. A reverse pair is (i, j) such that i < j and
 * arr[i] > 2 * arr[j].
 *
 * Coding Platforms:
 * - LeetCode (Reverse Pairs): https://leetcode.com/problems/reverse-pairs/
 * - GeeksForGeeks (Reverse Pairs):
 * https://www.geeksforgeeks.org/count-reverse-pairs-array/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/reverse-pairs_1116098
 * - CodeChef: https://www.codechef.com/practice
 *
 * Notes:
 * - Brute force: O(n^2)
 * - Optimal: O(n log n) using modified Merge Sort
 */
public class ReversePairs {

    public static void main(String[] args) {
        int[][] tests = {
                { 1, 3, 2, 3, 1 }, // 3 reverse pairs
                { 2, 4, 3, 5, 1 }, // 3 reverse pairs
                { 5, 4, 3, 2, 1 }, // 4 reverse pairs
                { 1, 2, 3, 4, 5 }, // 0 reverse pairs
                {} // 0 reverse pairs
        };

        int[] expected = { 3, 3, 4, 0, 0 };

        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(arr));
            System.out.println("Expected: " + expected[t]);

            long brute = countReversePairsBrute(arr);
            long optimal = countReversePairsMergeSort(arr);

            System.out.println("[Brute O(n^2)]       -> " + brute);
            System.out.println("[MergeSort O(nlogn)] -> " + optimal);
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force
     * Check every pair (i, j).
     * Time: O(n^2)
     * Space: O(1)
     */
    public static long countReversePairsBrute(int[] arr) {
        long count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > 2L * arr[j])
                    count++;
            }
        }
        return count;
    }

    /**
     * Approach 2: Modified Merge Sort
     * Algorithm:
     * 1. Divide array into two halves
     * 2. Recursively count in left and right halves
     * 3. Count cross pairs using two pointers
     * 4. Merge halves
     *
     * Time: O(n log n)
     * Space: O(n)
     */
    public static long countReversePairsMergeSort(int[] arr) {
        return mergeSort(arr, 0, arr.length - 1);
    }

    private static long mergeSort(int[] arr, int l, int r) {
        long count = 0;
        if (l < r) {
            int mid = l + (r - l) / 2;
            count += mergeSort(arr, l, mid);
            count += mergeSort(arr, mid + 1, r);
            count += countCrossPairs(arr, l, mid, r);
            merge(arr, l, mid, r);
        }
        return count;
    }

    // Count cross pairs between [l..mid] and [mid+1..r]
    private static long countCrossPairs(int[] arr, int l, int mid, int r) {
        long count = 0;
        int j = mid + 1;
        for (int i = l; i <= mid; i++) {
            while (j <= r && (long) arr[i] > 2L * arr[j]) {
                j++;
            }
            count += (j - (mid + 1));
        }
        return count;
    }

    // Standard merge step
    private static void merge(int[] arr, int l, int mid, int r) {
        int n1 = mid - l + 1, n2 = r - mid;
        int[] left = new int[n1], right = new int[n2];

        for (int i = 0; i < n1; i++)
            left[i] = arr[l + i];
        for (int j = 0; j < n2; j++)
            right[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (left[i] <= right[j])
                arr[k++] = left[i++];
            else
                arr[k++] = right[j++];
        }
        while (i < n1)
            arr[k++] = left[i++];
        while (j < n2)
            arr[k++] = right[j++];
    }
}

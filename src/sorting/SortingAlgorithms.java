package sorting;

import java.util.*;

/**
 * Problem:
 * Implement and compare different sorting algorithms.
 * Each algorithm should sort an array of integers in non-decreasing order.
 *
 * Coding Platform Example Links:
 * - Bubble Sort (LeetCode – Sort Colors example bubble sort):
 * https://leetcode.com/problems/sort-colors/discuss/155578/simplest-bubble-sort-solution
 * :contentReference[oaicite:2]{index=2}
 * - General Sorting Algorithm Implementations (GFG):
 * https://www.geeksforgeeks.org/dsa/sorting-in-array/
 * :contentReference[oaicite:3]{index=3}
 *
 * Widely Asked in Interviews:
 * - Bubble Sort
 * - Selection Sort
 * - Insertion Sort
 * - Merge Sort
 * - Quick Sort
 * - Heap Sort
 * - Counting Sort
 * - Radix Sort
 * 
 * Notes:
 * - Covers O(n^2), O(n log n), and O(n+k) sorts.
 * - Includes stability and complexity analysis for interview preparation.
 */

public class SortingAlgorithms {

    public static void main(String[] args) {
        int[][] tests = {
                { 5, 2, 9, 1, 5, 6 }, // random with duplicates
                { 1, 2, 3, 4, 5 }, // already sorted
                { 5, 4, 3, 2, 1 }, // reverse sorted
                { 1 }, // single element
                {} // empty
        };

        int[][] expected = {
                { 1, 2, 5, 5, 6, 9 },
                { 1, 2, 3, 4, 5 },
                { 1, 2, 3, 4, 5 },
                { 1 },
                {}
        };

        String[] sorts = {
                "Bubble", "Selection", "Insertion",
                "Merge", "Quick", "Heap",
                "Counting", "Radix"
        };

        for (int t = 0; t < tests.length; t++) {
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(tests[t]));
            System.out.println("Expected: " + Arrays.toString(expected[t]));

            for (String sort : sorts) {
                int[] arr = Arrays.copyOf(tests[t], tests[t].length);
                switch (sort) {
                    case "Bubble":
                        bubbleSort(arr);
                        break;
                    case "Selection":
                        selectionSort(arr);
                        break;
                    case "Insertion":
                        insertionSort(arr);
                        break;
                    case "Merge":
                        mergeSort(arr, 0, arr.length - 1);
                        break;
                    case "Quick":
                        quickSort(arr, 0, arr.length - 1);
                        break;
                    case "Heap":
                        heapSort(arr);
                        break;
                    case "Counting":
                        arr = countingSort(arr);
                        break;
                    case "Radix":
                        arr = radixSort(arr);
                        break;
                }
                System.out.println("[" + sort + "] -> " + Arrays.toString(arr));
            }
            System.out.println();
        }
    }

    /**
     * Bubble Sort:
     * - Repeatedly compare adjacent elements and swap if out of order.
     * - Largest element "bubbles up" to the end each pass.
     *
     * Complexity:
     * - Best: O(n) (if already sorted with early exit)
     * - Average: O(n^2)
     * - Worst: O(n^2)
     * - Space: O(1)
     * - Stable: Yes
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped)
                break; // Early exit if no swaps
        }
    }

    /**
     * Selection Sort:
     * - Repeatedly select the minimum element from unsorted part
     * and place it at the beginning.
     *
     * Complexity:
     * - Best: O(n^2)
     * - Average: O(n^2)
     * - Worst: O(n^2)
     * - Space: O(1)
     * - Stable: No
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(arr, i, minIdx);
            }
        }
    }

    /**
     * Insertion Sort:
     * - Build sorted array one element at a time.
     * - Take current element and insert it into correct position
     * among already-sorted left side.
     *
     * Complexity:
     * - Best: O(n) (already sorted)
     * - Average: O(n^2)
     * - Worst: O(n^2)
     * - Space: O(1)
     * - Stable: Yes
     */
    public static void insertionSort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * Merge Sort:
     * - Divide array into halves recursively.
     * - Sort each half and then merge them.
     *
     * Complexity:
     * - Best: O(n log n)
     * - Average: O(n log n)
     * - Worst: O(n log n)
     * - Space: O(n)
     * - Stable: Yes
     */
    public static void mergeSort(int[] arr, int l, int r) {
        if (l >= r)
            return; // base case: one element or empty → already sorted

        int mid = l + (r - l) / 2; // avoid overflow
        // sort left half
        mergeSort(arr, l, mid);
        // sort right half
        mergeSort(arr, mid + 1, r);
        // merge the two sorted halves
        merge(arr, l, mid, r);
    }

    /**
     * Merge two sorted halves [l..mid] and [mid+1..r].
     * Use two temporary arrays and merge with two pointers.
     */
    private static void merge(int[] arr, int l, int mid, int r) {
        // sizes of two halves
        int n1 = mid - l + 1;
        int n2 = r - mid;

        // create temp arrays
        int[] left = new int[n1];
        int[] right = new int[n2];

        // copy data into temp arrays
        for (int i = 0; i < n1; i++)
            left[i] = arr[l + i];
        for (int j = 0; j < n2; j++)
            right[j] = arr[mid + 1 + j];

        // merge temp arrays back into arr[l..r]
        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++]; // pick from left if smaller (<= keeps stability)
            } else {
                arr[k++] = right[j++];
            }
        }

        // copy remaining elements (only one of these loops will run)
        while (i < n1)
            arr[k++] = left[i++];
        while (j < n2)
            arr[k++] = right[j++];
    }

    /**
     * Quick Sort:
     * - Pick a pivot element.
     * - Partition array so smaller elements go left, larger go right.
     * - Recursively sort left and right.
     *
     * Complexity:
     * - Best: O(n log n)
     * - Average: O(n log n)
     * - Worst: O(n^2) (bad pivot choices)
     * - Space: O(log n) recursion
     * - Stable: No
     */
    public static void quickSort(int[] arr, int l, int r) {
        // TODO
    }

    /**
     * Heap Sort:
     * - Build max-heap.
     * - Swap root (max) with last, reduce heap size, heapify again.
     * - Repeat until sorted.
     *
     * Complexity:
     * - Best: O(n log n)
     * - Average: O(n log n)
     * - Worst: O(n log n)
     * - Space: O(1)
     * - Stable: No
     */
    public static void heapSort(int[] arr) {
        // TODO
    }

    /**
     * Counting Sort:
     * - Works only for integers in a limited range.
     * - Count frequency of each element, then rebuild sorted array.
     *
     * Complexity:
     * - Best: O(n + k)
     * - Average: O(n + k)
     * - Worst: O(n + k) (k = max - min value range)
     * - Space: O(n + k)
     * - Stable: Yes (if implemented properly)
     */
    public static int[] countingSort(int[] arr) {
        // TODO
        return arr;
    }

    /**
     * Radix Sort:
     * - Non-comparison sort for integers.
     * - Sort numbers digit by digit using a stable sort (like Counting Sort).
     *
     * Complexity:
     * - Best: O(n * d)
     * - Average: O(n * d)
     * - Worst: O(n * d) (d = number of digits)
     * - Space: O(n + k)
     * - Stable: Yes
     */
    public static int[] radixSort(int[] arr) {
        // TODO
        return arr;
    }

    /** Helper Functions */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
